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
import com.example.qhhq.adapter.NewsAdapter;
import com.example.qhhq.bean.News;
import com.example.qhhq.present.INewsPresent;
import com.example.qhhq.present.impl.INewsPresentImpl;
import com.example.qhhq.view.INewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/25.
 */

public class NewsActivity extends Activity implements INewsView, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private NewsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<News> beanList = new ArrayList<>();
    private int delayMillis = 1000;

    private INewsPresent newsPresent;

    BuildBean buildBean;   //加载中的 dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_type_one);

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        TextView tv = (TextView) findViewById(R.id.tv_main_title);
        tv.setText("资讯");
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
        adapter = new NewsAdapter(R.layout.img_left_three_text_right, beanList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //添加headview
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsActivity.this,WebViewActivity1.class);
                intent.putExtra("url",beanList.get(position).getUrl());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);

        //获取数据
        newsPresent = new INewsPresentImpl(this,this);
        newsPresent.getInfoList();
    }


    @Override
    public void onRefresh() {
        newsPresent.getInfoList();
    }


    @Override
    public void showInfoList(List<News> infoList) {

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
}
