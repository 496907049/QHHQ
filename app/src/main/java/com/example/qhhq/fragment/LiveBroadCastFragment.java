package com.example.qhhq.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.example.qhhq.R;
import com.example.qhhq.activity.LiveBroadCastListActivity;
import com.example.qhhq.activity.VideoWebViewActivity;
import com.example.qhhq.adapter.FinanceAdapter;
import com.example.qhhq.adapter.ForeignExchangeAdapter;
import com.example.qhhq.bean.LiveBroadCast;
import com.example.qhhq.present.ILiveBroadCastPresent;
import com.example.qhhq.present.impl.ILiveBroadCastPresentImpl;
import com.example.qhhq.view.ILiveBroadCastView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/19.
 */

public class LiveBroadCastFragment extends Fragment implements ILiveBroadCastView,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{

    private RecyclerView finaLiveBroadRecyView;
    private final  int financeLiveBroadCastCId = 139;
    private final  int financeLiveBroadCastMarkId= 0;
    private int financeLiveBroadCastNum = 10;
    private View finaLiveBroadView;
    private  BaseQuickAdapter financeAdapter;
    private List<LiveBroadCast> financeBeanList = new ArrayList<>();

    private final  int foreignExchangeLiveBroadCastCId = 144;
    private final  int foreignExchangeLiveBroadCastMarkId= 0;
    private int foreignExchangeLiveBroadCastNum = 10;
    private RecyclerView  foreignExchangeRecyclerView;
    private ForeignExchangeAdapter foreignExchangeAdapter;
    private SwipeRefreshLayout foreignExchangeSwipeRefreshLayout;
    private List<LiveBroadCast> foreignExchangeBeanList = new ArrayList<>();

    private ILiveBroadCastPresent liveBroadCastPresent ;

    private View fragmentView;
    private int delayMillis = 1000;

    BuildBean buildBean;   //加载中的 dialog

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView= inflater.inflate(R.layout.fragment_live_broadcast,container,false);
        //更改title标题
        TextView titleView = (TextView) fragmentView.findViewById(R.id.tv_main_title);
        titleView.setText(getResources().getString(R.string.live_broadcast));

        buildBean = DialogUIUtils.showLoading(getActivity(), "加载中...", false, true, false, true);
        buildBean.show();

        liveBroadCastPresent = new ILiveBroadCastPresentImpl(this,getActivity());
        initLiveBroadcast(inflater,container);

        return fragmentView;
    }

    /***
     *
     * @param inflater
     * @param container
     */
    private void initLiveBroadcast(LayoutInflater inflater,ViewGroup container){
        //初始化  上方的财经直播
        finaLiveBroadView = inflater.inflate(R.layout.fragment_finance_live_broadcast,container,false);
        LinearLayout financeMoreLL = (LinearLayout) finaLiveBroadView.findViewById(R.id.ll_finance_more);
        LinearLayout foreExchMoreLL = (LinearLayout) finaLiveBroadView.findViewById(R.id.ll_foreign_exchange_more);
        financeMoreLL.setOnClickListener(this);
        foreExchMoreLL.setOnClickListener(this);
        finaLiveBroadRecyView = (RecyclerView) finaLiveBroadView.findViewById(R.id.rv_fina_live_broad);
        //一行显示几个
        finaLiveBroadRecyView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        financeAdapter = new FinanceAdapter(R.layout.fragment_finance_live_broadcast_adapter, financeBeanList);
        financeAdapter.openLoadAnimation();
        financeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),VideoWebViewActivity.class);
                intent.putExtra("url",financeBeanList.get(position).getUrl());
                startActivity(intent);
            }
        });
        finaLiveBroadRecyView.setAdapter(financeAdapter);

        /* 初始化外汇控件 */
        foreignExchangeRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_live_broad_cast);
        foreignExchangeSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_live_broad_cast);
        foreignExchangeSwipeRefreshLayout.setOnRefreshListener(this);
        foreignExchangeSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        foreignExchangeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foreignExchangeAdapter = new ForeignExchangeAdapter(R.layout.fragment_foreign_exchange_adapter,foreignExchangeBeanList);
        foreignExchangeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //添加headview
        foreignExchangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),VideoWebViewActivity.class);
                intent.putExtra("url",foreignExchangeBeanList.get(position).getUrl());
                startActivity(intent);
            }
        });
        foreignExchangeRecyclerView.setAdapter(foreignExchangeAdapter);
        foreignExchangeAdapter.addHeaderView(finaLiveBroadView);


        //获取数据
        liveBroadCastPresent.getFinanceLiveBroadCastInfoList(financeLiveBroadCastCId,financeLiveBroadCastMarkId,financeLiveBroadCastNum);
    }

    /*  回调得到财经直播的参数  */
    @Override
    public void showFinanceLiveBroadCastInfoList(List<LiveBroadCast> infoList) {
        financeBeanList.clear();
        for(int i = 0 ;i<= 2; i++){
            financeBeanList.add(infoList.get(i));
        }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    financeAdapter.setNewData(financeBeanList);
                }
            }, delayMillis);
        liveBroadCastPresent.getForeignExchangeInfoList(foreignExchangeLiveBroadCastCId,foreignExchangeLiveBroadCastMarkId,foreignExchangeLiveBroadCastNum);
    }

    /*  回调得到外汇直播的参数  */
    @Override
    public void showForeignExchangeInfoList(final List<LiveBroadCast> infoList) {
        foreignExchangeBeanList = infoList;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    foreignExchangeAdapter.setNewData(foreignExchangeBeanList);
                    foreignExchangeSwipeRefreshLayout.setRefreshing(false);
                    DialogUIUtils.dismiss(buildBean);
                }
            }, delayMillis);

    }

    @Override
    public void showRefreshInfoList(List<LiveBroadCast> infoList) {

    }

    @Override
    public void showLoadMoreInfoList(List<LiveBroadCast> infoList) {

    }


    @Override
    public void onRefresh() {
        liveBroadCastPresent.getFinanceLiveBroadCastInfoList(financeLiveBroadCastCId,financeLiveBroadCastMarkId,financeLiveBroadCastNum);
//        liveBroadCastPresent.getForeignExchangeInfoList(foreignExchangeLiveBroadCastCId,foreignExchangeLiveBroadCastMarkId,foreignExchangeLiveBroadCastNum);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_finance_more:
                Intent financeIntent = new Intent(getContext(),LiveBroadCastListActivity.class);
                financeIntent.putExtra("liveBroadCastType","finance");
                startActivity(financeIntent);
                break;
            case R.id.ll_foreign_exchange_more:
                Intent foreignExchangeIntent = new Intent(getContext(),LiveBroadCastListActivity.class);
                foreignExchangeIntent.putExtra("liveBroadCastType","foreignExchange");
                startActivity(foreignExchangeIntent);
                break;
        }
    }
}
