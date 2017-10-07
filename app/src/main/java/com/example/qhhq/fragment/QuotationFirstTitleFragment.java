package com.example.qhhq.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Util.ViewFindUtils;
import com.example.qhhq.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/19.
 */

public class QuotationFirstTitleFragment extends Fragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "布伦特原油", "WTI原油", "外汇", "全球指数", "国际金", "上金所", "伦敦金属"
    };
    private MyPagerAdapter mAdapter;

    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_quotation_first_title,container,false);

        fragmentManager =getActivity().getSupportFragmentManager();
            mFragments.add(new SimpleCardFragment1());
            mFragments.add(new SimpleCardFragment2());
            mFragments.add(new SimpleCardFragment3());
            mFragments.add(new SimpleCardFragment4());
            mFragments.add(new SimpleCardFragment5());
            mFragments.add(new SimpleCardFragment6());
            mFragments.add(new SimpleCardFragment7());

        ViewPager vp = (ViewPager) fragmentView.findViewById(R.id.vp);
        vp.setOffscreenPageLimit(mFragments.size()-1);
        mAdapter = new MyPagerAdapter(fragmentManager);
        vp.setAdapter(mAdapter);

        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(fragmentView, R.id.tl_2);
        tabLayout_2.setViewPager(vp);
        return fragmentView;
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment)super.instantiateItem(container,position);
            fragmentManager.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            Fragment fragment = mFragments.get(position);
            fragmentManager.beginTransaction().hide(fragment).commit();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            fragment = mFragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id",""+position);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

}

