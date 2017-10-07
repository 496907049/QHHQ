package com.example.qhhq.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.Util.Contants;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.R;
import com.example.qhhq.adapter.SimpleCarAdapter;
import com.example.qhhq.bean.Price;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SimpleCardFragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View fragmentView;
    private RecyclerView mRecyView;
    private BaseQuickAdapter simpleCarAdapter;
    private List<Price> priceList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static SimpleCardFragment3 getInit( ) {
        SimpleCardFragment3 sf = new SimpleCardFragment3();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_simple_car, null);
   /* 初始化外汇控件 */
        mRecyView = (RecyclerView) fragmentView.findViewById(R.id.rv_live_broad_cast);
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_live_broad_cast);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyView.setLayoutManager(new LinearLayoutManager(getContext()));
        simpleCarAdapter = new SimpleCarAdapter(R.layout.fragment_quotation_first_title_adapter,priceList);
        simpleCarAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //添加headview
        mRecyView.setAdapter(simpleCarAdapter);

        getInfoList();
        return fragmentView;
    }

    void showInfoList(List<Price> beanList){
        priceList = beanList;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simpleCarAdapter.setNewData(priceList);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    public void getInfoList() {
        String url =  Contants.HANG_PING_HTTP_BASE3;
        HttpClientUtil.getInstance(getActivity()).get(url,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                Gson gson = new Gson();
                try {
                    showInfoList((List<Price>) gson.fromJson(string,new TypeToken<List<Price>>(){}.getType()));
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

    @Override
    public void onRefresh() {
        getInfoList();
    }
}