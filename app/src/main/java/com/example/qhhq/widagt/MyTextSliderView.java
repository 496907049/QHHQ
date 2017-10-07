package com.example.qhhq.widagt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.qhhq.R;

/**
 * Created by asus01 on 2017/9/26.
 */

public class MyTextSliderView extends BaseSliderView{

    public MyTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_render_type_text,null);
        ImageView target = (ImageView)v.findViewById(R.id.my_daimajia_slider_image);
        bindEventAndShow(v, target);
        return v;
    }
}
