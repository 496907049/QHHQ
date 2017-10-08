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
import com.example.qhhq.bean.FederalReserve;
import com.example.qhhq.bean.NewsInformation;

import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class NewsInformationAdapter extends BaseQuickAdapter<NewsInformation, BaseViewHolder> {

    public NewsInformationAdapter(@LayoutRes int layoutResId, @Nullable List<NewsInformation> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInformation item) {
        helper.setText(R.id.tv1,item.getTitle());
        helper.setText(R.id.tv2, TextLengthUtil.textLengthTo20(item.getDescription()));
        try {
            helper.setText(R.id.tv3,"时间："+ DateUtil.timedate(item.getAddTime()));
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
