package com.example.qhhq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.qhhq.R;
import com.example.qhhq.activity.CCTTActivity;
import com.example.qhhq.activity.InternetActivity;
import com.example.qhhq.activity.MacroscopicActivity;
import com.example.qhhq.activity.NewsActivity;

/**
 * Created by asus01 on 2017/9/19.
 */

public class FindFragment extends Fragment implements View.OnClickListener{

    private LinearLayout newsLL;   //资讯
    private LinearLayout macroscopicLL;   //宏观
    private LinearLayout CCTVLL;   //央视
    private LinearLayout internetLL;   //互联网

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_find,container,false);

        newsLL = (LinearLayout) fragmentView.findViewById(R.id.ll_zi_xun);
        newsLL.setOnClickListener(this);
        macroscopicLL = (LinearLayout) fragmentView.findViewById(R.id.ll_hong_guan);
        macroscopicLL.setOnClickListener(this);
        CCTVLL = (LinearLayout) fragmentView.findViewById(R.id.ll_yang_shi);
        CCTVLL.setOnClickListener(this);
        internetLL = (LinearLayout) fragmentView.findViewById(R.id.ll_internet);
        internetLL.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_zi_xun:
                Intent intent = new Intent(getActivity(),NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_hong_guan:
                Intent intent2 = new Intent(getActivity(),MacroscopicActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_yang_shi:
                Intent intent3 = new Intent(getActivity(),CCTTActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_internet:
                Intent intent4 = new Intent(getActivity(),InternetActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
