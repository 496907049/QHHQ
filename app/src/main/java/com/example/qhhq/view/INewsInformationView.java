package com.example.qhhq.view;

import com.example.qhhq.bean.News;
import com.example.qhhq.bean.NewsInformation;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface INewsInformationView {

    void showInfoList(List<NewsInformation> infoList);

    void showLoadMoreInfoList(List<NewsInformation> infoList);
}
