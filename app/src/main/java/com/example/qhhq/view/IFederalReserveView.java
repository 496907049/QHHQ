package com.example.qhhq.view;

import com.example.qhhq.bean.FederalReserve;
import com.example.qhhq.bean.News;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface IFederalReserveView {

    void showInfoList(List<FederalReserve> infoList);

    void showLoadMoreInfoList(List<FederalReserve> infoList);
}
