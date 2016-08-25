package com.example.chenggggg.a2014designnewslearn;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by Chenggggg on 2016/8/25.
 */
public class AppBarLayoutActivity extends FragmentActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyclerview;

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private ArrayList<Fragment> mFragments;
    private String[] mTitles = new String[]{"天地","玄黄","宇宙","洪荒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbarlayout);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.color_2095f2));
        initFragment();
        initView();
    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        for (int i = 0; i < 4; i++) {
            mFragments.add(new RcycleViewFragment());
        }
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar_zhihu);
        mToolbar.inflateMenu(R.menu.toolbar_menu);

        mViewpager = (ViewPager) findViewById(R.id.vp);
        mViewPagerAdapter madapter = new mViewPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(madapter);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTablayout.setTabTextColors(Color.WHITE,Color.DKGRAY);
        mTablayout.setupWithViewPager(mViewpager);
    }

    private class mViewPagerAdapter extends FragmentPagerAdapter {

        public mViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}