package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.example.qhhq.R;
import com.example.qhhq.adapter.ForeignExchange2Adapter;
import com.example.qhhq.adapter.NewsAdapter;
import com.example.qhhq.bean.ForeignExchange;
import com.example.qhhq.bean.Macroscopic;
import com.example.qhhq.bean.News;
import com.example.qhhq.present.IForeignExchangePresent;
import com.example.qhhq.present.impl.IForeignExchangePresentImpl;
import com.example.qhhq.view.IForeignExchangeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/25.
 */

public class ForeignExchangeActivity extends Activity implements IForeignExchangeView, SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRecyclerView;
    private ForeignExchange2Adapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<ForeignExchange> beanList = new ArrayList<>();
    private int delayMillis = 1000;

    private IForeignExchangePresent newsPresent;

    private int markId=0;
    private int num=10;
    private int tagId=7;

    BuildBean buildBean;   //加载中的 dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_type_one);

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        TextView tv = (TextView) findViewById(R.id.tv_main_title);
        tv.setText("外汇");
        ImageView imageView = (ImageView) findViewById(R.id.iv_main_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForeignExchange2Adapter(R.layout.img_left_three_text_right, beanList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //添加headview
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ForeignExchangeActivity.this,WebViewActivity.class);
                intent.putExtra("url",beanList.get(position).getUrl());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);

        //获取数据
        newsPresent = new IForeignExchangePresentImpl(this,this);
        newsPresent.getInfoList(markId,num,tagId);
    }


    @Override
    public void onRefresh() {
        num = 10;
        newsPresent.getInfoList(markId,num,tagId);
    }


    @Override
    public void showInfoList(List<ForeignExchange> infoList) {
        beanList = infoList;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setNewData(beanList);
                mSwipeRefreshLayout.setRefreshing(false);
                DialogUIUtils.dismiss(buildBean);
            }
        }, delayMillis);
    }

    @Override
    public void showLoadMoreInfoList(List<ForeignExchange> infoList) {
        mSwipeRefreshLayout.setEnabled(false);
        if (infoList.size() <= 0) {
            adapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
        } else {
            List<ForeignExchange> infoList2 = new ArrayList<>();
            for(int i = infoList.size()-10;i<infoList.size();i++){
                infoList2.add(infoList.get(i));
            }
            adapter.addData(infoList2);   //添加数据
            adapter.loadMoreComplete();
        }
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onLoadMoreRequested() {
        num += 10;
        newsPresent.getInfoList(markId,num,tagId);
    }
}
