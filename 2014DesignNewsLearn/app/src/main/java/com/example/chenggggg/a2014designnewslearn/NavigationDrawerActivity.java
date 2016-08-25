package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

/**
 * Created by Chenggggg on 2016/8/24.
 */
public class NavigationDrawerActivity extends android.app.Activity {

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setItemIconTintList(null);
    }
}
