package com.example.qhhq.widagt;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * Created by asus01 on 2017/10/7.
 */

public interface  OpenFileChooserCallBack {

    void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

    void openFileChooserCallBack(ValueCallback<Uri[]> uploadMsg,WebChromeClient.FileChooserParams fileChooserParams);

}
