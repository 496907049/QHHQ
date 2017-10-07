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
import com.example.qhhq.adapter.LiveBroadCastListAdapter;
import com.example.qhhq.bean.LiveBroadCast;
import com.example.qhhq.present.ILiveBroadCastPresent;
import com.example.qhhq.present.impl.ILiveBroadCastPresentImpl;
import com.example.qhhq.view.ILiveBroadCastView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/23.
 */

public class LiveBroadCastListActivity extends Activity implements ILiveBroadCastView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private final int financeLiveBroadCastCId = 139;
    private final int financeLiveBroadCastMarkId = 0;
    private int financeLiveBroadCastNum = 10;
    BaseQuickAdapter financeAdapter;
    private List<LiveBroadCast> beanList = new ArrayList<>();

    private final int foreignExchangeLiveBroadCastCId = 144;
    private final int foreignExchangeLiveBroadCastMarkId = 0;
    private int foreignExchangeLiveBroadCastNum = 10;
    BaseQuickAdapter foreignExchangeAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String type = "";
    private RecyclerView mRecyclerView;
    private ILiveBroadCastPresent liveBroadCastPresent;

    private static final int TOTAL_COUNTER = 18;
    private static final int PAGE_SIZE = 6;
    private int delayMillis = 1000;

    BuildBean buildBean;   //加载中的 dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_broad_cast_list);

        TextView textView = (TextView) findViewById(R.id.tv_main_title);
        textView.setText("财金直播");
        RelativeLayout rlView = (RelativeLayout) findViewById(R.id.rl_main_back);
        rlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buildBean = DialogUIUtils.showLoading(this, "加载中...", false, true, false, true);
        buildBean.show();

        //从Intent当中根据key取得value
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("liveBroadCastType");
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        liveBroadCastPresent = new ILiveBroadCastPresentImpl(this, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_live_broad_cast_activity);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        if ("finance".equals(type)) {
            financeAdapter = new LiveBroadCastListAdapter(R.layout.activity_live_broad_cast_list_adapter, beanList);
            financeAdapter.setOnLoadMoreListener(this, mRecyclerView);
            financeAdapter.openLoadAnimation();
            financeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(LiveBroadCastListActivity.this,VideoWebViewActivity.class);
                    intent.putExtra("url",beanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(financeAdapter);
            financeAdapter.setEnableLoadMore(true);

            liveBroadCastPresent.getFinanceLiveBroadCastInfoList(financeLiveBroadCastCId, financeLiveBroadCastMarkId, financeLiveBroadCastNum);
        } else {
            foreignExchangeAdapter = new LiveBroadCastListAdapter(R.layout.activity_live_broad_cast_list_adapter, beanList);
            foreignExchangeAdapter.setOnLoadMoreListener(this, mRecyclerView);
            foreignExchangeAdapter.openLoadAnimation();
            foreignExchangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(LiveBroadCastListActivity.this,VideoWebViewActivity.class);
                    intent.putExtra("url",beanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(foreignExchangeAdapter);
            liveBroadCastPresent.getForeignExchangeInfoList(foreignExchangeLiveBroadCastCId, foreignExchangeLiveBroadCastMarkId, foreignExchangeLiveBroadCastNum);
        }

    }

    @Override
    public void showFinanceLiveBroadCastInfoList(final List<LiveBroadCast> infoList) {
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
    public void showForeignExchangeInfoList(final List<LiveBroadCast> infoList) {
        beanList = infoList;
        foreignExchangeAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                foreignExchangeAdapter.setNewData(beanList);
                mSwipeRefreshLayout.setRefreshing(false);
                foreignExchangeAdapter.setEnableLoadMore(true);
                DialogUIUtils.dismiss(buildBean);
            }
        }, delayMillis);
    }

    @Override
    public void showRefreshInfoList(final List<LiveBroadCast> infoList) {
        beanList = infoList;
        if ("finance".equals(type)) {
            financeAdapter.setEnableLoadMore(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    financeAdapter.setNewData(beanList);
                    mSwipeRefreshLayout.setRefreshing(false);
                    financeAdapter.setEnableLoadMore(true);
                }
            }, delayMillis);
        } else {

            foreignExchangeAdapter.setEnableLoadMore(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    foreignExchangeAdapter.setNewData(beanList);
                    mSwipeRefreshLayout.setRefreshing(false);
                    foreignExchangeAdapter.setEnableLoadMore(true);
                }
            }, delayMillis);
        }

    }

    @Override
    public void showLoadMoreInfoList(List<LiveBroadCast> infoList) {
        mSwipeRefreshLayout.setEnabled(false);
        if ("finance".equals(type)) {
            if (infoList.size() <= 0) {
                financeAdapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
            } else {
                List<LiveBroadCast> infoList2 = new ArrayList<>();
                for(int i = infoList.size()-10;i<infoList.size();i++){
                    infoList2.add(infoList.get(i));
                }
                financeAdapter.addData(infoList2);   //添加数据
                financeAdapter.loadMoreComplete();
            }
        } else {
            if (infoList.size() <= 0) {
                foreignExchangeAdapter.loadMoreEnd(false);//true is gone,false is visible    //没有更多数据
            } else {
                List<LiveBroadCast> infoList2 = new ArrayList<>();
                for(int i = infoList.size()-10;i<infoList.size();i++){
                    infoList2.add(infoList.get(i));
                }
                foreignExchangeAdapter.addData(infoList2);   //添加数据
                foreignExchangeAdapter.loadMoreComplete();
            }
        }
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onRefresh() {
        beanList.clear();
        if ("finance".equals(type)) {
            financeLiveBroadCastNum = 10;
            liveBroadCastPresent.getRefreshInfoList(financeLiveBroadCastCId, financeLiveBroadCastMarkId, financeLiveBroadCastNum);

        } else {
            foreignExchangeLiveBroadCastNum = 10;
            liveBroadCastPresent.getRefreshInfoList(foreignExchangeLiveBroadCastCId, foreignExchangeLiveBroadCastMarkId, foreignExchangeLiveBroadCastNum);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if ("finance".equals(type)) {
            financeLiveBroadCastNum += 10;
            liveBroadCastPresent.getLoadMoreInfoList(financeLiveBroadCastCId, financeLiveBroadCastMarkId, financeLiveBroadCastNum);

        } else {
            foreignExchangeLiveBroadCastNum += 10;
            liveBroadCastPresent.getLoadMoreInfoList(foreignExchangeLiveBroadCastCId, foreignExchangeLiveBroadCastMarkId, foreignExchangeLiveBroadCastNum);
        }
    }
}
