package com.demo.jcguan.sprogressbar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by jcguan on 2016/8/4.
 */
public class RoundProgressbar extends HorizantalProgressbar {

    public RoundProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressbar(Context context) {
        this(context,null);
    }

    public RoundProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

}
