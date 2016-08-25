package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chenggggg on 2016/8/25.
 */
public class RcycleViewFragment extends Fragment {

    private ArrayList<String> mData = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recyler_layout,null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        view.setLayoutManager(linearLayoutManager);
        view.setAdapter(new mRecyclerViewAdapter());
        return view;
    }

    private class mRecyclerViewAdapter extends RecyclerView.Adapter<mRecyclerViewAdapter.mViewHolder> {


        @Override
        public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.recytlerview_text, parent, false);
            mViewHolder holder = new mViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(mViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        public class mViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public mViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.textView);
            }
        }

    }


    private void initData() {
        for (int i = 0; i < 100; i++) {
            mData.add("item   " + i);
        }
    }


}
