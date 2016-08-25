package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Chenggggg on 2016/8/23.
 */
public class TabLayoutActivity extends AppCompatActivity {

    private String[] titles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5"};
    private TabLayout tab;
    private ViewPager vp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_layout);

        vp = (ViewPager) findViewById(R.id.vp_tablayout);
        vp.setAdapter(new mAdapter(getSupportFragmentManager()));

        tab = (TabLayout) findViewById(R.id.tablayout);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tab.setBackgroundColor(Color.RED);
//        tab.setSelectedTabIndicatorHeight(22);
//        tab.setSelectedTabIndicatorHeight(100);
//        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setupWithViewPager(vp);
    }


    private class mAdapter extends FragmentPagerAdapter {

        public mAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            TabLayoutFragment.newInstance(position);
            return TabLayoutFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
