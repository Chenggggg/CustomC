package com.example.chenggggg.a2014designnewslearn;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Chenggggg on 2016/8/25.
 */
public class CoordinatorLayoutActivity extends FragmentActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerview;

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private ArrayList<Fragment> mFragments;
    private String[] mTitles = new String[]{"天地","玄黄","宇宙","洪荒"};
    private FloatingActionButton mFloatingActionButton;
    private LinearLayout leftMenu;
    private boolean toggleon;
    private DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);
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
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_zhihu);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_about:
                        Snackbar.make(mFloatingActionButton,"I am snackBar",Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notification:
                        startActivity(new Intent(CoordinatorLayoutActivity.this,collapsingToolbarLayoutActivity.class));
                        break;
                }
                return false;
            }
        });

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


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

    public void onClickFab(View view){
        Snackbar.make(mFloatingActionButton,"I am snackBar",Snackbar.LENGTH_SHORT).setAction("土司", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"点击了snackbar",Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

}