package com.example.chenggggg.a2014designnewslearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private String[] mTitle = new String[]{"TabLayoutTest","ToolBarTest",
            "Translucent(Image)","Translucent(Color)","ZhihuActivity","NavigationDrawer",
    "Recyclerview","snackbar&&floatingactionbutton","AppBarLayout","Coordinatorlayout加以上综合",
    };
    private ArrayList<Activity> mActivities = new ArrayList<Activity>(){};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

        mListView = (ListView) findViewById(R.id.lv);
        mListView.setAdapter(new mAdapter());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Activity targetActivity = mActivities.get(i);
                startActivity(new Intent(MainActivity.this,targetActivity.getClass()));
            }
        });
    }

    private void initActivity() {
        mActivities.add(new TabLayoutActivity());
        mActivities.add(new TooBarActivity());
        mActivities.add(new TransLucentImageActivity());
        mActivities.add(new TransLucentColorActivity());
        mActivities.add(new ZhihuActivity());
        mActivities.add(new NavigationDrawerActivity());
        mActivities.add(new RecyclerviewActivity());
        mActivities.add(new snackbarFABActivity());
        mActivities.add(new AppBarLayoutActivity());
        mActivities.add(new CoordinatorLayoutActivity());

    }


    private class mAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public Object getItem(int i) {
            return mActivities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            TextView textView = new TextView(MainActivity.this);
            textView.setText(mTitle[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(20,20,20,20);
            textView.setTextSize(33);
            return textView;
        }

    }
}
