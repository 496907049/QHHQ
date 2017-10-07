package com.example.qhhq.view;

import com.example.qhhq.bean.CCTV;
import com.example.qhhq.bean.Macroscopic;

import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public interface ICCTVView {

    void showInfoList(List<CCTV> infoList);

    void showLoadMoreInfoList(List<CCTV> infoList);

}
