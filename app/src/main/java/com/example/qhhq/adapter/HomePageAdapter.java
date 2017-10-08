package com.example.qhhq.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.Util.DateUtil;
import com.example.Util.TextLengthUtil;
import com.example.Util.ToastUtils;
import com.example.Util.Utils;
import com.example.qhhq.R;
import com.example.qhhq.bean.HomePage;

import java.util.List;

/**
 * Created by asus01 on 2017/9/22.
 */

public class HomePageAdapter extends BaseQuickAdapter<HomePage, BaseViewHolder> {

    public HomePageAdapter(@LayoutRes int layoutResId, @Nullable List<HomePage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePage item) {
        helper.setText(R.id.tv1,item.getTitle());
        helper.setText(R.id.tv2, TextLengthUtil.textLengthTo35(item.getDescription()));
        try {
            helper.setText(R.id.tv3,"时间："+ DateUtil.timedate(item.getAddtime()));
        }catch (Exception e){
            helper.setText(R.id.tv3,"时间");
        }
        // 加载网络图片
        Glide.with(mContext).load(item.getThumb()).crossFade().into((ImageView) helper.getView(R.id.img1));
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);
        }
    };
}

