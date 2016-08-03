package com.demo.jcguan.myviewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.demo.jcguan.myviewpagerindicator.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private ArrayList<VpSimpleFragment> mContents = new ArrayList<VpSimpleFragment>();
    private FragmentPagerAdapter mAdapter;
    private static final String TAG = "MainActivity";


    //Arrays.asList 将一个数组转化为一个List对象，这个方法会返回一个ArrayList类型的对象，
    // 这个ArrayList类并非java.util.ArrayList类，而是Arrays类的静态内部类！
    // 用这个对象对列表进行添加删除更新操作，就会报UnsupportedOperationException异常。
    private List<String> mTitles = Arrays.asList("新闻1", "幽默2", "天气3", "新闻4", "幽默5", "天气6", "新闻7", "幽默8", "天气9");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initView();
        initData();

        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initView() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
    }

    private void initData() {

        for (String title : mTitles) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            Log.d(TAG, "initData: title = " + title);
            mContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };

    }


}
