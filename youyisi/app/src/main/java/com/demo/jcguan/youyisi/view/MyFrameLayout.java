package com.demo.jcguan.youyisi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by jcguan on 2016/8/6.
 */
public class MyFrameLayout extends FrameLayout {

    private DrapView mDrapView;

    public MyFrameLayout(Context context) {
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDraglayout(DrapView mDragLayout){
        this.mDrapView = mDragLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        // 如果当前是关闭状态, 按之前方法判断
        if(mDrapView.getStatus() == DrapView.Status.Close){
            return super.onInterceptTouchEvent(ev);
        }else {
            return true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 如果当前是关闭状态, 按之前方法处理
        if(mDrapView.getStatus() == DrapView.Status.Close){
            return super.onTouchEvent(event);
        }else {
            // 手指抬起, 执行关闭操作
            if(event.getAction() == MotionEvent.ACTION_UP){
                mDrapView.close();
            }

            return true;
        }
    }


}
