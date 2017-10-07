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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.example.qhhq.R;
import com.example.qhhq.adapter.CCTVAdapter;
import com.example.qhhq.adapter.LiveBroadCastListAdapter;
import com.example.qhhq.bean.CCTV;
import com.example.qhhq.present.ICCTVPresent;
import com.example.qhhq.present.impl.ICCTVPresentImpl;
import com.example.qhhq.view.ICCTVView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/23.
 */

public class CCTTActivity extends Activity implements ICCTVView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private int markId=0;
    private int num=10;
    private int tagId=16;

    BaseQuickAdapter financeAdapter;
    private List<CCTV> beanList = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String type = "";
    private RecyclerView mRecyclerView;
    private ICCTVPresent cctvPresent;

    private int delayMillis = 1000;

    BuildBean buildBean;   //加载中的 dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccvt_list);

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        TextView textView = (TextView) findViewById(R.id.tv_main_title);
        textView.setText("央视");
        RelativeLayout rlView = (RelativeLayout) findViewById(R.id.rl_main_back);
        rlView.setOnClickListener(new View.OnClickListener() {
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

        cctvPresent = new ICCTVPresentImpl(this, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_live_broad_cast_activity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            financeAdapter = new CCTVAdapter(R.layout.activity_cctv_adapter, beanList);
            financeAdapter.setOnLoadMoreListener(this, mRecyclerView);
            financeAdapter.openLoadAnimation();
            financeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(CCTTActivity.this,WebViewActivity.class);
                    intent.putExtra("url",beanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(financeAdapter);
            financeAdapter.setEnableLoadMore(true);

        cctvPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onRefresh() {
        beanList.clear();
            num = 10;
        cctvPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onLoadMoreRequested() {
        num += 10;
        cctvPresent.getLoadMoreInfoList(markId, num, tagId);
    }

    @Override
    public void showInfoList(List<CCTV> infoList) {
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
    public void showLoadMoreInfoList(List<CCTV> infoList) {
        mSwipeRefreshLayout.setEnabled(false);
            if (infoList.size() <= 0) {
                financeAdapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
            } else {
                List<CCTV> infoList2 = new ArrayList<>();
                for(int i = infoList.size()-10;i<infoList.size();i++){
                    infoList2.add(infoList.get(i));
                }
                financeAdapter.addData(infoList2);   //添加数据
                financeAdapter.loadMoreComplete();
            }
        mSwipeRefreshLayout.setEnabled(true);
    }
}
