package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Chenggggg on 2016/8/26.
 */
public class collapsingToolbarLayoutActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout1 = mCollapsingToolbarLayout;
        mCollapsingToolbarLayout1.setTitle("Chenggggg 吃苦瓜");
        initAdapter();
    }

    private void initAdapter() {
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 5);
        manager.setOrientation(GridLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new mRvAdapter());
    }

    private class mRvAdapter extends RecyclerView.Adapter<mRvAdapter.mViewHolder> {


        @Override
        public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.imageview_layout, parent, false);
            mViewHolder holder = new mViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(mViewHolder holder, int position) {
            holder.mImageView.setPadding(5, 5, 5, 5);
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        public class mViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageView;

            public mViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }


}
