package com.demo.jcguan.youyisi.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jcguan on 2016/8/6.
 */
public class otherUtils {
    public static Toast mToast;

    public static void showSingleToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
