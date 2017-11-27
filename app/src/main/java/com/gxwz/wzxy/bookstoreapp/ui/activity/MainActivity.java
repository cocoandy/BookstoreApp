package com.gxwz.wzxy.bookstoreapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gxwz.wzxy.bookstoreapp.R;
import com.gxwz.wzxy.bookstoreapp.adapter.CinemaTabAdapter;
import com.gxwz.wzxy.bookstoreapp.base.BaseActivity;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.HomeFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.MineFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.OrderFragment;
import com.gxwz.wzxy.bookstoreapp.ui.frgment.ShoppCarFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_title)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private FragmentPagerAdapter fAdapter; //定义adapter
    private List<Fragment> fragments = new ArrayList<>();//定义要装fragment的列表
    private List<String> titles = new ArrayList<>(); //tab名称列表

    HomeFragment homeFragment;
    ShoppCarFragment shoppCarFragment;
    OrderFragment orderFragment;
    MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    public void initData() {


        homeFragment = new HomeFragment();
        shoppCarFragment = new ShoppCarFragment();
        orderFragment = new OrderFragment();
        mineFragment = new MineFragment();

        titles.add(getString(R.string.main_home));
        titles.add(getString(R.string.main_shoppcar));
        titles.add(getString(R.string.main_order));
        titles.add(getString(R.string.main_mine));

        fragments.add(homeFragment);
        fragments.add(shoppCarFragment);
        fragments.add(orderFragment);
        fragments.add(mineFragment);
        fAdapter = new CinemaTabAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(fAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.removeAllTabs();
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_home).setTag(1));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_soppcar).setTag(2));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_order).setTag(3));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.selector_mine).setTag(4));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
