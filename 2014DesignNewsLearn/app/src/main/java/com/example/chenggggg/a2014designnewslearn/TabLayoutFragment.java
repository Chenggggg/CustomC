package com.example.chenggggg.a2014designnewslearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chenggggg on 2016/8/23.
 */
public class TabLayoutFragment extends Fragment {

    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static TabLayoutFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        TabLayoutFragment fragment = new TabLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("第"+mPage+"页");
        textView.setTextSize(50);
        textView.setGravity(Gravity.CENTER);
        return view;
    }
}
