package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;

/**
 * Created by Chenggggg on 2016/8/24.
 */
public class ZhihuActivity extends android.app.Activity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zhihu_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_zhihu);
        mToolbar.inflateMenu(R.menu.zhihu_menu);
    }
}
