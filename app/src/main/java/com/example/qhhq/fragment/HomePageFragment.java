package com.example.qhhq.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.qhhq.R;
import com.example.qhhq.activity.CrudeOilActivity;
import com.example.qhhq.activity.FederalReserveActivity;
import com.example.qhhq.activity.InternationalFormActivity;
import com.example.qhhq.activity.NewsActivity;
import com.example.qhhq.activity.NewsInformationActivity;
import com.example.qhhq.activity.NobleMetalActivity;
import com.example.qhhq.activity.WebViewActivity;
import com.example.qhhq.adapter.HomePageAdapter;
import com.example.qhhq.bean.HomePage;
import com.example.qhhq.bean.News;
import com.example.qhhq.present.IHomePagePresent;
import com.example.qhhq.present.impl.IHomePageIPresentmpl;
import com.example.qhhq.view.IHomePageView;
import com.example.qhhq.widagt.MyTextSliderView;

import java.util.ArrayList;
import java.util.List;


public class HomePageFragment extends Fragment implements IHomePageView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SliderLayout sliderLayout;
    private PagerIndicator indicator;

    private HomePageAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int delayMillis = 1000;

    private IHomePagePresent homePagePresent;
    private List<HomePage> beanList = new ArrayList<>();

    private int markId=0;
    private int num=10;
    private int tagId=19;

    private ImageView imgZiXun;
    private ImageView imgMeiLianChu;
    private ImageView imgYuanYou;
    private ImageView imgGuiJingShu;
    private LinearLayout llNewsInformation;
    private LinearLayout llInternationalForm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //首页轮播图
        View imageSliderView = inflater.inflate(R.layout.fragment_home_imge_slider, container, false);
        initImageSlider(imageSliderView);
        //设置轮播图高度
        SliderLayout sliderLayout = (SliderLayout) imageSliderView.findViewById(R.id.slider);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sliderLayout.getLayoutParams();
        params.height = 300;
        imageSliderView.setLayoutParams(params);

        //中间的功能图
        View functionView = inflater.inflate(R.layout.fragment_home_page_function, container, false);
        imgZiXun = (ImageView) functionView.findViewById(R.id.img_zi_xun);
        imgMeiLianChu = (ImageView) functionView.findViewById(R.id.img_mei_lian_chu);
        imgYuanYou = (ImageView) functionView.findViewById(R.id.img_yuan_you);
        imgGuiJingShu = (ImageView) functionView.findViewById(R.id.img_gui_jing_shu);
        llNewsInformation = (LinearLayout) functionView.findViewById(R.id.ll_news_information);
        llInternationalForm = (LinearLayout) functionView.findViewById(R.id.ll_international_form);
        imgZiXun.setOnClickListener(this);
        imgMeiLianChu.setOnClickListener(this);
        imgYuanYou.setOnClickListener(this);
        imgGuiJingShu.setOnClickListener(this);
        llNewsInformation.setOnClickListener(this);
        llInternationalForm.setOnClickListener(this);

        //下面的显示界面图  listview
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //创建布局管理
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        //初始化adapter
        initAdapter();

        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(imageSliderView);
        adapter.addHeaderView(functionView);

        homePagePresent = new IHomePageIPresentmpl(this,getActivity());
        homePagePresent.getInfoList(markId,num,tagId);

        return view;
    }

    /**
     * 初始化首页的商品广告条
     */
    private void initImageSlider(View view) {
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        //准备好要显示的数据
        List<String> imageUrls = new ArrayList<>();
        final List<String> descriptions = new ArrayList<>();
        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");
        descriptions.add("");
        descriptions.add("");
        descriptions.add("");

        for (int i = 0; i < imageUrls.size(); i++) {
            //新建三个展示View，并且添加到SliderLayout
            MyTextSliderView tsv = new MyTextSliderView(getActivity());
            tsv.image(imageUrls.get(i)).description(descriptions.get(i));
            final int finalI = i;
            tsv.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
//                    Toast.makeText(getActivity(), descriptions.get(finalI), Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSlider(tsv);
        }

        //对SliderLayout进行一些自定义的配置
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setDuration(3000);
        //      sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomIndicator(indicator);
    }

    private void initAdapter() {
        adapter = new HomePageAdapter(R.layout.img_left_three_text_right, beanList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("url",beanList.get(position).getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        num = 10;
        homePagePresent.getInfoList(markId,num,tagId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_zi_xun:
                Intent intent = new Intent(getActivity(),NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.img_mei_lian_chu:
                Intent intent2 = new Intent(getActivity(),FederalReserveActivity.class);
                startActivity(intent2);
                break;
            case R.id.img_yuan_you:
                Intent intent3 = new Intent(getActivity(),CrudeOilActivity.class);
                startActivity(intent3);
                break;
            case R.id.img_gui_jing_shu:
                Intent intent4 = new Intent(getActivity(),NobleMetalActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_news_information:
                Intent intent5 = new Intent(getActivity(),NewsInformationActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_international_form:
                Intent intent6 = new Intent(getActivity(),InternationalFormActivity.class);
                startActivity(intent6);
                break;
        }
    }

    @Override
    public void showInfoList(List<HomePage> infoList) {
        beanList = infoList;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setNewData(beanList);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, delayMillis);
    }

}
