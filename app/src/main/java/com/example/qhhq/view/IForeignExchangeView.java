package com.example.qhhq.view;

import com.example.qhhq.bean.ForeignExchange;
import com.example.qhhq.bean.LiveBroadCast;
import com.example.qhhq.bean.News;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface IForeignExchangeView {

    void showInfoList(List<ForeignExchange> infoList);

    void showLoadMoreInfoList(List<ForeignExchange> infoList);

}
