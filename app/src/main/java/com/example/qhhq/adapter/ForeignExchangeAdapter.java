package com.example.qhhq.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.Util.DateUtil;
import com.example.Util.ToastUtils;
import com.example.Util.Utils;
import com.example.qhhq.R;
import com.example.qhhq.bean.LiveBroadCast;

import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class ForeignExchangeAdapter extends BaseQuickAdapter<LiveBroadCast, BaseViewHolder> {

    public ForeignExchangeAdapter(@LayoutRes int layoutResId, @Nullable List<LiveBroadCast> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveBroadCast item) {
        helper.setText(R.id.tv_title_fore_excha,item.getTile());
        helper.setText(R.id.tv_titme_fore_excha,"发布时间:"+ DateUtil.timedate(item.getPublishTime()));
        // 加载网络图片
        Glide.with(mContext).load(item.getPictureUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_fore_excha));
        helper.setImageResource(R.id.iv_vido_go_fore_excha,R.drawable.vido_go);
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
