package com.example.qhhq.present.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.Util.HttpClientUtil;
import com.example.qhhq.bean.FirstInterface;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.present.ILoginPresent;
import com.example.qhhq.view.ILoginView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public class ILoginPresentImpl implements ILoginPresent {

    private Context context;
    private ILoginView loginView;

    public ILoginPresentImpl(ILoginView loginView, Context context) {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void getFirstInterface() {
        String url = "http://hy8998.com/Home/Outs/index/mchid/591035b264848.html";
        HttpClientUtil.getInstance(context).get(url,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                try {
                    Gson gson = new Gson();
                    JSONObject Info = new JSONObject(string);
                    JSONObject root = new JSONObject(Info.getString("msg"));
                    String bb = root.toString();
                    FirstInterface firstInterface  = gson.fromJson(bb,FirstInterface.class);
                     loginView.showFirstInterface(firstInterface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }
}
