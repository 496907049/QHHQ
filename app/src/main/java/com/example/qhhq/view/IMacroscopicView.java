package com.example.qhhq.view;

import com.example.qhhq.bean.Macroscopic;
import com.example.qhhq.bean.News;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface IMacroscopicView {

    void showInfoList(List<Macroscopic> infoList);

    void showLoadMoreInfoList(List<Macroscopic> infoList);

}
