package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Chenggggg on 2016/8/25.
 */
public class snackbarFABActivity extends android.app.Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_fab_layout);
    }

    public void onClickFab(View view ){
        Snackbar.make(findViewById(R.id.fab_add),"Snackbar  Test",Snackbar.LENGTH_SHORT).show();
    }
}
