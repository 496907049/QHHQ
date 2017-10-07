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
import com.example.qhhq.adapter.CrudeOilAdapter;
import com.example.qhhq.bean.CrudeOil;
import com.example.qhhq.present.ICrudeOilPresent;
import com.example.qhhq.present.impl.ICrudeOilPresentImpl;
import com.example.qhhq.view.ICrudeOilView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/23.
 */

public class CrudeOilActivity extends Activity implements ICrudeOilView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private int markId=0;
    private int num=10;
    private int tagId=5;

    BaseQuickAdapter adapter;
    private List<CrudeOil> beanList = new ArrayList<>();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private ICrudeOilPresent crudeOilPresent;

    private int delayMillis = 1000;

    BuildBean buildBean;   //加载中的 dialog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crude_oil);

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        TextView textView = (TextView) findViewById(R.id.tv_main_title);
        textView.setText("原油");
        ImageView imageView = (ImageView) findViewById(R.id.iv_main_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        crudeOilPresent = new ICrudeOilPresentImpl(this, this) ;

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_oil_activity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new CrudeOilAdapter(R.layout.activity_crude_oil_adapter, beanList);
            adapter.setOnLoadMoreListener(this, mRecyclerView);
            adapter.openLoadAnimation();
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(CrudeOilActivity.this,WebViewActivity.class);
                    intent.putExtra("url",beanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(adapter);
            adapter.setEnableLoadMore(true);

        crudeOilPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onRefresh() {
        beanList.clear();
            num = 10;
        crudeOilPresent.getInfoList(markId, num, tagId);

    }

    @Override
    public void onLoadMoreRequested() {
        num += 10;
        crudeOilPresent.getLoadMoreInfoList(markId, num, tagId);
    }

    @Override
    public void showInfoList(List<CrudeOil> infoList) {
            beanList = infoList;
            adapter.setEnableLoadMore(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.setNewData(beanList);
                    mSwipeRefreshLayout.setRefreshing(false);
                    adapter.setEnableLoadMore(true);
                    DialogUIUtils.dismiss(buildBean);
                }
            }, delayMillis);

    }

    @Override
    public void showLoadMoreInfoList(List<CrudeOil> infoList) {
        mSwipeRefreshLayout.setEnabled(false);
            if (infoList.size() <= 0) {
                adapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
            } else {
                List<CrudeOil> infoList2 = new ArrayList<>();
                for(int i = infoList.size()-10;i<infoList.size();i++){
                    infoList2.add(infoList.get(i));
                }
                adapter.addData(infoList2);   //添加数据
                adapter.loadMoreComplete();
            }
        mSwipeRefreshLayout.setEnabled(true);
    }
}
