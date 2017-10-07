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
import com.example.Util.ToastUtils;
import com.example.Util.Utils;
import com.example.qhhq.R;
import com.example.qhhq.bean.LiveBroadCast;
import com.example.qhhq.bean.Price;

import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class SimpleCarAdapter extends BaseQuickAdapter<Price, BaseViewHolder> {

    public SimpleCarAdapter(@LayoutRes int layoutResId, @Nullable List<Price> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Price item) {
        helper.setText(R.id.tv_shares_name_by_quot_first,item.getName());
        helper.setText(R.id.tv_price_high,item.getHigh());
        helper.setText(R.id.tv_price_last,item.getLast());
        helper.setText(R.id.tv_price_buy,item.getBuy());
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
