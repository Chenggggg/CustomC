package com.demo.jcguan.myviewpagerindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jcguan on 2016/8/2.
 */
public class VpSimpleFragment extends Fragment {

    private String mTitle;
    private final static String BUNDLE_TITLE = "title";
    private static final String TAG = "VpSimpleFragment";


    //@Nullable 表示定义的字段可以为空
//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
//            Log.d(TAG, "onCreateView: mTitle is not null");
        }

        TextView textView=new TextView(getActivity());
        textView.setText(mTitle);
        textView.setTextColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }


    //实例化对象
    public static VpSimpleFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE,title);

        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

}
