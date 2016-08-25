package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chenggggg on 2016/8/24.
 */
public class RecyclerviewActivity extends android.app.Activity {


    private RecyclerView mRecyclerView;
    private ArrayList<String> mData = new ArrayList<String>();
    private mAdapter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recylerview_layout);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        GridLayoutManager mManager = new GridLayoutManager(getApplication(),10);
        mManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mManager);

//        LinearLayoutManager mManager = new LinearLayoutManager(this);
//        mManager.setOrientation(OrientationHelper.VERTICAL);
//        mManager.setOrientation(OrientationHelper.HORIZONTAL);
//        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(adpter = new mAdapter());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.addItemDecoration(new DividerItemDecoration(RecyclerviewActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(RecyclerviewActivity.this));

    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mData.add("item  " + i);
        }
    }


    private class mAdapter extends RecyclerView.Adapter<mAdapter.mViewHolder> {


        private View view;

        @Override
        public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            view = View.inflate(getApplication(), R.layout.fragment_page, null);
            view = LayoutInflater.from(RecyclerviewActivity.this).inflate(R.layout.recytlerview_text, parent, false);
            mViewHolder mHolder = new mViewHolder(view);
            return mHolder;
        }

        @Override
        public void onBindViewHolder(mViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));
            holder.mTextView.setTextSize(30);
            holder.mTextView.setGravity(Gravity.CENTER);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class mViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public mViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.textView);
            }
        }

    }
}


