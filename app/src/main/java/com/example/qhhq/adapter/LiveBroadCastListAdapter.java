package com.example.qhhq.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.qhhq.R;
import com.example.qhhq.bean.LiveBroadCast;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class LiveBroadCastListAdapter extends BaseQuickAdapter<LiveBroadCast, BaseViewHolder> {
    public LiveBroadCastListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveBroadCast item) {
        helper.setText(R.id.text, item.getTile());
        // 加载网络图片
        Glide.with(mContext).load(item.getPictureUrl()).crossFade().into((ImageView) helper.getView(R.id.icon));

    }
}
