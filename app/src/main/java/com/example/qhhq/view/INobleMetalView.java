package com.example.qhhq.view;

import com.example.qhhq.bean.NobleMetal;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface INobleMetalView {

    void showInfoList(List<NobleMetal> infoList);

    void showLoadMoreInfoList(List<NobleMetal> infoList);

}
