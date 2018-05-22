package com.zhailiw.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zhailiw.app.R;
import com.zhailiw.app.view.activity.FavorActivity;
import com.zhailiw.app.view.activity.OrderActivity;
import com.zhailiw.app.view.fragment.AllFragment;
import com.zhailiw.app.view.fragment.ArticleFragment;
import com.zhailiw.app.view.fragment.DoneFragment;
import com.zhailiw.app.view.fragment.FavorFragment;
import com.zhailiw.app.view.fragment.NotPayFragment;
import com.zhailiw.app.view.fragment.PayFragment;

import java.util.ArrayList;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class OrderPresenter extends BasePresenter  {
    private static final String TAG = OrderPresenter.class.getSimpleName();
    private final BasePresenter basePresenter;
    private OrderActivity activity;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments;
    private FragmentPagerAdapter fragmentPagerAdapter;

    public OrderPresenter(Context context){
        super(context);
        activity = (OrderActivity) context;
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init() {
        TabLayout tabLayout =  activity.findViewById(R.id.tabLayout);
        viewpager =  activity.findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(AllFragment.getInstance());
        fragments.add(NotPayFragment.getInstance());
        fragments.add(PayFragment.getInstance());
        fragments.add(DoneFragment.getInstance());

        fragmentPagerAdapter = new FragmentPagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewpager.setAdapter(fragmentPagerAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(0).setText("全部");
        tabLayout.getTabAt(1).setText("未付款");
        tabLayout.getTabAt(2).setText("已付款");
        tabLayout.getTabAt(3).setText("已完成");
    }

}
