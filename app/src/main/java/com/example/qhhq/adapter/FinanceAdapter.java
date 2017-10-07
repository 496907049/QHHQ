package com.example.qhhq.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.qhhq.R;
import com.example.qhhq.bean.LiveBroadCast;

import java.util.List;

/**
 * Created by asus01 on 2017/9/23.
 */

public class FinanceAdapter extends BaseQuickAdapter<LiveBroadCast, BaseViewHolder> {

    public FinanceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveBroadCast item) {
        helper.setText(R.id.text, item.getTile());
        // 加载网络图片
        Glide.with(mContext).load(item.getPictureUrl()).crossFade().into((ImageView) helper.getView(R.id.icon));
    }
}
