package com.example.Util;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.qhhq.R;
import com.example.qhhq.activity.WebViewActivity2;

/**
 * Created by asus01 on 2017/10/10.
 */

public class PhoneStatusBarColorUtil {

    public static String change(Activity activity) {

//        try {
//            //改变安卓状态栏颜色代码
//            Window window = activity.getWindow();
//            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //设置状态栏颜色
//            window.setStatusBarColor(activity.getColor(R.color.black));
//            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
//            View mChildView = mContentView.getChildAt(0);
//            if (mChildView != null) {
//                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
//                ViewCompat.setFitsSystemWindows(mChildView, true);
//            }
//        }catch (Exception e){
//            Window window = activity.getWindow();
//            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
//
//            //First translucent status bar.
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            int statusBarHeight = 10;
//
//            View mChildView = mContentView.getChildAt(0);
//            if (mChildView != null) {
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
//                //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
//                if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
//                    //不预留系统空间
//                    ViewCompat.setFitsSystemWindows(mChildView, false);
//                    lp.topMargin += statusBarHeight;
//                    mChildView.setLayoutParams(lp);
//                }
//            }
//
//            View statusBarView = mContentView.getChildAt(0);
//            if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
//                //避免重复调用时多次添加 View
//                statusBarView.setBackgroundColor(activity.getColor(R.color.black));
//                return null;
//            }
//            statusBarView = new View(activity);
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//            statusBarView.setBackgroundColor(activity.getColor(R.color.black));
////向 ContentView 中添加假 View
//            mContentView.addView(statusBarView, 0, lp);
//        }







        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //First translucent status bar.
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int statusBarHeight = 10;

        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
            if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                //不预留系统空间
                ViewCompat.setFitsSystemWindows(mChildView, false);
                lp.topMargin += statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }

        View statusBarView = mContentView.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
            //避免重复调用时多次添加 View
            statusBarView.setBackgroundColor(activity.getColor(R.color.black));
            return null;
        }
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusBarView.setBackgroundColor(activity.getColor(R.color.black));
//向 ContentView 中添加假 View
        mContentView.addView(statusBarView, 0, lp);



        return null;
    }




}
