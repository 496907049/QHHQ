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
import com.example.qhhq.bean.ExchangeRate;
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
public class QuotationSecendAdapter extends BaseQuickAdapter<ExchangeRate, BaseViewHolder> {

    private String[] names={
           "AUD", "CAD", "CHF", "DKK", "EUR", "GBP", "HKD", "INR", "JPY", "KRW",
           "MOP", "MYP", "NOK", "NZD", "PHP", "RUB", "SEK", "SGD", "THB", "TWD",
            "USD"
    };

    private int[] img={
            R.drawable.aud,R.drawable.cad,R.drawable.chf,R.drawable.dkk,R.drawable.eur,
            R.drawable.gbp,R.drawable.hkd,R.drawable.inr,R.drawable.jpy,R.drawable.krw,
            R.drawable.mop,R.drawable.myp,R.drawable.nok,R.drawable.nzd,R.drawable.php,
            R.drawable.rub,R.drawable.sek,R.drawable.sgd,R.drawable.thb,R.drawable.twd,
            R.drawable.usd
    };

    public QuotationSecendAdapter(@LayoutRes int layoutResId, @Nullable List<ExchangeRate> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExchangeRate item) {
        helper.setText(R.id.tv_exchange_rate,item.getMoney()+" "+item.getCode()+" = "+item.getRmb()+" CNY");
        helper.setText(R.id.tv_money_type,item.getName());
        for(int i = 0; i<names.length;i++){
            if (names[i].equals(item.getCode())){
                helper.setImageResource(R.id.iv_second_quot_icon,img[i]);
            }
        }

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
