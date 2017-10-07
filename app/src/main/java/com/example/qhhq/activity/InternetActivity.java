package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.example.qhhq.R;
import com.example.qhhq.adapter.CCTVAdapter;
import com.example.qhhq.adapter.InternetAdapter;
import com.example.qhhq.bean.CCTV;
import com.example.qhhq.bean.Internet;
import com.example.qhhq.present.ICCTVPresent;
import com.example.qhhq.present.IInternetPresent;
import com.example.qhhq.present.impl.ICCTVPresentImpl;
import com.example.qhhq.present.impl.IInternetPresentImpl;
import com.example.qhhq.view.ICCTVView;
import com.example.qhhq.view.IInternetView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/23.
 */

public class InternetActivity extends Activity implements IInternetView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private int markId=0;
    private int num=10;
    private int tagId=16;

    BaseQuickAdapter financeAdapter;
    private List<Internet> beanList = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String type = "";
    private RecyclerView mRecyclerView;
    private IInternetPresent internetPresent;

    private int delayMillis = 1000;

    BuildBean buildBean;   //加载中的 dialog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_list);

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        TextView textView = (TextView) findViewById(R.id.tv_main_title);
        textView.setText("互联网");
        ImageView imageView = (ImageView) findViewById(R.id.iv_main_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //从Intent当中根据key取得value
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("liveBroadCastType");
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        internetPresent = new IInternetPresentImpl(this, this) ;

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_internet_activity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            financeAdapter = new InternetAdapter(R.layout.activity_internet_adapter, beanList);
            financeAdapter.setOnLoadMoreListener(this, mRecyclerView);
            financeAdapter.openLoadAnimation();
            financeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(InternetActivity.this,WebViewActivity.class);
                    intent.putExtra("url",beanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(financeAdapter);
            financeAdapter.setEnableLoadMore(true);

        internetPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onRefresh() {
        beanList.clear();
            num = 10;
        internetPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onLoadMoreRequested() {
        num += 10;
        internetPresent.getLoadMoreInfoList(markId, num, tagId);
    }

    @Override
    public void showInfoList(List<Internet> infoList) {
            beanList = infoList;
            financeAdapter.setEnableLoadMore(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    financeAdapter.setNewData(beanList);
                    mSwipeRefreshLayout.setRefreshing(false);
                    financeAdapter.setEnableLoadMore(true);
                    DialogUIUtils.dismiss(buildBean);
                }
            }, delayMillis);

    }

    @Override
    public void showLoadMoreInfoList(List<Internet> infoList) {
        mSwipeRefreshLayout.setEnabled(false);
            if (infoList.size() <= 0) {
                financeAdapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
            } else {
                List<Internet> infoList2 = new ArrayList<>();
                for(int i = infoList.size()-10;i<infoList.size();i++){
                    infoList2.add(infoList.get(i));
                }
                financeAdapter.addData(infoList2);   //添加数据
                financeAdapter.loadMoreComplete();
            }
        mSwipeRefreshLayout.setEnabled(true);
    }
}
