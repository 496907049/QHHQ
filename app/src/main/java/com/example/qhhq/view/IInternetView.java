package com.example.qhhq.view;

import com.example.qhhq.bean.CCTV;
import com.example.qhhq.bean.Internet;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface IInternetView {

    void showInfoList(List<Internet> infoList);

    void showLoadMoreInfoList(List<Internet> infoList);

}
