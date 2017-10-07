package com.example.qhhq.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus01 on 2017/9/27.
 */

public class SafeSwitch extends BmobObject {

    String Link;
    String IsOpen;
    String BundleID;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getBundleID() {
        return BundleID;
    }

    public void setBundleID(String bundleID) {
        BundleID = bundleID;
    }


    public String getIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(String isOpen) {
        IsOpen = isOpen;
    }

}
