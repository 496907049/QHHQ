package com.example.qhhq.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.example.Util.ToastUtils;
import com.example.qhhq.R;
import com.example.qhhq.activity.FuturesActivity;
import com.example.qhhq.activity.InternetActivity;
import com.example.qhhq.activity.MineProductActivity;
import com.example.qhhq.activity.WebViewActivity;

/**
 * Created by asus01 on 2017/9/19.
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    LinearLayout llProduct;
    LinearLayout llfutures;
    LinearLayout llClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_mine,container,false);

        llProduct = (LinearLayout) fragmentView.findViewById(R.id.ll_mine_product);
        llfutures = (LinearLayout) fragmentView.findViewById(R.id.ll_mine_futures);
        llClear = (LinearLayout) fragmentView.findViewById(R.id.ll_mine_clear);

        llProduct.setOnClickListener(this);
        llfutures.setOnClickListener(this);
        llClear.setOnClickListener(this);

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_mine_product:
                Intent intent = new Intent(getActivity(),MineProductActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mine_futures:
                Intent intent2 = new Intent(getActivity(),FuturesActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_mine_clear:
                DialogUIUtils.showAlert(getActivity(),null, "清理完毕", "", "", "确定", "", true, true, true, new DialogUIListener() {
                    @Override
                    public void onPositive() {
                    }

                    @Override
                    public void onNegative() {
                    }

                }).show();
                break;
        }
    }
}
