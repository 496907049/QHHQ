package com.example.qhhq.view;

import com.example.qhhq.bean.InternationalForm;
import com.example.qhhq.bean.NewsInformation;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface IInternationalFormView {

    void showInfoList(List<InternationalForm> infoList);

    void showLoadMoreInfoList(List<InternationalForm> infoList);
}
