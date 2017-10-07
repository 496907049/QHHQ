package com.example.qhhq.present;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.qhhq.bean.LiveBroadCast;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface ILiveBroadCastPresent {

    void getFinanceLiveBroadCastInfoList(int cId,int markId,int num);

    void getForeignExchangeInfoList(int cId,int markId,int num);

    void getRefreshInfoList(int cId,int markId,int num);

    void getLoadMoreInfoList(int cId,int markId,int num);

}
