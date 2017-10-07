package com.example.qhhq.present.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.qhhq.R;
import com.example.qhhq.fragment.FindFragment;
import com.example.qhhq.fragment.HomePageFragment;
import com.example.qhhq.fragment.LiveBroadCastFragment;
import com.example.qhhq.fragment.MineFragment;
import com.example.qhhq.fragment.QuotationFragment;
import com.example.qhhq.present.IMainPresent;

/**
 * Created by asus01 on 2017/9/20.
 */

public class IMainPresentImpl implements IMainPresent,RadioGroup.OnCheckedChangeListener{

    private Context context;
    private Fragment[] mFragments;
    private RadioGroup mRadioGroup;
    private  FragmentManager manager;

    public IMainPresentImpl(Context context, FragmentManager manager, RadioGroup radioGroup) {
        this.context = context;
        this.mRadioGroup = radioGroup;
        this.manager = manager;
        initFragment();
    }

    public void initFragment() {
        mRadioGroup.setOnCheckedChangeListener(this);
        //初始化要显示的Fragment数组
        mFragments = new Fragment[5];
        mFragments[0] = new HomePageFragment();
        mFragments[1] = new QuotationFragment();
        mFragments[2] = new LiveBroadCastFragment();
        mFragments[3] = new FindFragment();
        mFragments[4] = new MineFragment();
        //获取Fragment管理器

        //获取事物(使用v4包下)
        FragmentTransaction  transaction=manager.beginTransaction();
        //默认选中HomepageFragment替换Framelayout
        transaction.replace(R.id.fl_radio_show, mFragments[0]);
        //提交事件
        transaction.commit();
        //默认被点击的首页
        mRadioGroup.check(R.id.rb_by_home_page);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        //写法与默认点击页面的相同
//        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction  transaction=manager.beginTransaction();
        switch (checkedId) {
            case R.id.rb_by_home_page:
                transaction.replace(R.id.fl_radio_show, mFragments[0]);
                break;
            case R.id.rb_radio_quotation:
                transaction.replace(R.id.fl_radio_show, mFragments[1]);
                break;
            case R.id.rb_radio_live_broadcast:
                transaction.replace(R.id.fl_radio_show, mFragments[2]);
                break;
            case R.id.rb_radio_find:
                transaction.replace(R.id.fl_radio_show, mFragments[3]);
                break;
            case R.id.rb_radio_mine:
                transaction.replace(R.id.fl_radio_show, mFragments[4]);
                break;
        }
        transaction.commit();
    }
}
