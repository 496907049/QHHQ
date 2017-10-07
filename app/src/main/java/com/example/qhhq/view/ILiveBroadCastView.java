package com.example.qhhq.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.qhhq.bean.LiveBroadCast;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface ILiveBroadCastView {

    void showFinanceLiveBroadCastInfoList(List<LiveBroadCast> infoList);

    void showForeignExchangeInfoList(List<LiveBroadCast> infoList);

    void showRefreshInfoList(List<LiveBroadCast> infoList);

    void showLoadMoreInfoList(List<LiveBroadCast> infoList);

}
