package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.view.Window;

/**
 * Created by Chenggggg on 2016/8/23.
 */
public class TransLucentColorActivity extends android.app.Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.translucent_color_layout);

    }
}
