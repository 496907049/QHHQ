package com.example.qhhq.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.Util.DateUtil;
import com.example.Util.TextLengthUtil;
import com.example.qhhq.R;
import com.example.qhhq.bean.CCTV;
import com.example.qhhq.bean.CrudeOil;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CrudeOilAdapter extends BaseQuickAdapter<CrudeOil, BaseViewHolder> {
    public CrudeOilAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CrudeOil item) {
        helper.setText(R.id.tv_title,  TextLengthUtil.textLengthTo5(item.getTitle()));
        helper.setText(R.id.tv_content, TextLengthUtil.textLengthTo10(item.getDescription()));
        try {
            helper.setText(R.id.tv_time, DateUtil.timedate(item.getAddTime()));
        }catch (Exception e){
            helper.setText(R.id.tv_time, "");
        }
      ;
        // 加载网络图片
        Glide.with(mContext).load(item.getThumb()).crossFade().into((ImageView) helper.getView(R.id.icon));

    }
}
