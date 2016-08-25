package com.example.chenggggg.a2014designnewslearn;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Chenggggg on 2016/8/23.
 */
public class TooBarActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.toolbar_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setBackgroundColor(Color.rgb(100, 999, 01));
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolbar.setTitle("主标题！！1");
        mToolbar.setSubtitle("副标题");

        mToolbar.inflateMenu(R.menu.toolbar_menu);


        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_about:
                        Toast.makeText(getApplication(), "关于", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
