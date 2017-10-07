package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.Util.ToastUtils;
import com.example.qhhq.R;
import com.example.qhhq.bean.SafeSwitch;
import com.example.qhhq.bean.FirstInterface;
import com.example.qhhq.present.ILoginPresent;
import com.example.qhhq.present.impl.ILoginPresentImpl;
import com.example.qhhq.view.ILoginView;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 * Created by asus01 on 2017/9/19.
 */

public class LoginActivity extends  Activity implements ILoginView {

    ILoginPresent loginPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresent = new ILoginPresentImpl(this,this);
        loginPresent.getFirstInterface();

    }

    @Override
    public void showFirstInterface(final FirstInterface bean) {
        if("0".equals(bean.getOpen())){
            //第一：默认初始化
            Bmob.initialize(this, "d863879d3fa3d1dde9324801be8f500d");
            BmobQuery<SafeSwitch> bmobQuery = new BmobQuery<SafeSwitch>();
            bmobQuery.getObject("cYiH999U", new QueryListener<SafeSwitch>() {
                @Override
                public void done(SafeSwitch object, BmobException e) {
                    if(e==null){
//                        ToastUtils.show(LoginActivity.this,"查询成功");
                                    if("false".equals(object.getIsOpen())){
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(LoginActivity.this,WebViewActivity2.class);
                                        intent.putExtra("url",object.getLink());
                                        startActivity(intent);
                                    }
                    }else{
//                        ToastUtils.show(LoginActivity.this,"查询失败：" + e.getMessage());
                    }
                }
            });
        }else{
            Intent intent = new Intent(LoginActivity.this,WebViewActivity2.class);
            intent.putExtra("url",bean.getLinks());
            startActivity(intent);
        }

    }
}
