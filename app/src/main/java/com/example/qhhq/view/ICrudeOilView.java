package com.example.qhhq.view;

import com.example.qhhq.bean.CCTV;
import com.example.qhhq.bean.CrudeOil;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface ICrudeOilView {

    void showInfoList(List<CrudeOil> infoList);

    void showLoadMoreInfoList(List<CrudeOil> infoList);

}
