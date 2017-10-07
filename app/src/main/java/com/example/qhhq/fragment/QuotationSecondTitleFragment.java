package com.example.qhhq.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.R;
import com.example.qhhq.adapter.QuotationSecendAdapter;
import com.example.qhhq.bean.ExchangeRate;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/19.
 */

public class QuotationSecondTitleFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private QuotationSecendAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ExchangeRate> exchangeRateList = new ArrayList<>();

    BuildBean buildBean;   //加载中的 dialog

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_quotation_second_title,container,false);

        buildBean = DialogUIUtils.showLoading(getActivity(), "加载中...", false, true, false, true);
        buildBean.show();
 /* 初始化外汇控件 */
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_second_quta);
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_second_quta);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new QuotationSecendAdapter(R.layout.fragment_quot_second_adapter,exchangeRateList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //添加headview
        mRecyclerView.setAdapter(mAdapter);

        getInfoList();

        return fragmentView;
    }


    public void getInfoList() {
        String url = "http://htmdata.fx678.com/15/w/forex.php?s=859ea7d0069d1baa32d0e4eb38a881d1&time=1499058817&key=85f2816dc77bbc78651074e73606bf0d";
        HttpClientUtil.getInstance(getActivity()).get(url,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                Gson gson = new Gson();
                try {
                    showInfoList((List<ExchangeRate>) gson.fromJson(string,new TypeToken<List<ExchangeRate>>(){}.getType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }

    void showInfoList(List<ExchangeRate> beanList){
        exchangeRateList = beanList;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setNewData(exchangeRateList);
                mSwipeRefreshLayout.setRefreshing(false);
                DialogUIUtils.dismiss(buildBean);
            }
        }, 1000);
    }
}
