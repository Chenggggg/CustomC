package com.demo.jcguan.youyisi.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jcguan on 2016/8/5.
 */
public class DrapView extends FrameLayout {

    private ViewDragHelper mDrager;
    private static final String TAG = "DrapView";
    private int mWidth;
    private int mHeight;
    private int mRange;
    private View mLeftContent;
    private View mMainContent;
    private int mMainLeft;


    public DrapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrapView(Context context) {
        this(context, null);
    }

    public DrapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //第二个参数是敏感度，而touchslop是最小敏感距离，敏感度越大touchslop值越小越敏感，1.0f是正常
        //可直接调用两个参数的构造方法，默认敏感度为1.0
        mDrager = ViewDragHelper.create(this, 1.0f, mCallback);
    }


    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG, "tryCaptureView: chile" + child + "pointerId :" + pointerId);
            //返回true表示可以捕获该view，，，可依据child参数确定哪些view可以被捕获
            return child instanceof ViewGroup;
        }

        //当捕获的view能被拖拽时后调用，，上面那个方法有可能无法拖拽，只是尝试
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);

        }

        //返回拖拽的范围，不对拖拽进行真正的限制，仅仅决定动画的执行速度
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mWidth;
        }

        //该方法可用于对child移动的水平边界进行控制，left，top分别表示即将移动到的位置
        //在此处最小>=paddingleft   最大<=Viewgroup.getWidth() - paddingRight
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            Log.d(TAG, "clampViewPositionHorizontal: left: " + left + "dx : " + dx);
            if (child instanceof ViewGroup) {
                left = fixLeft(left);
            }
            if (child == mLeftContent) {
                mMainLeft += dx;
            }
            return left;

        }

        //该方法中对child移动的竖直边界进行控制，top表示即将移动到的位置
        //在此处最小>=paddingleft   最大<=Viewgroup.getWidth() - paddingRight
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }


        //当view位置改变的时候调用  处理要做的事情  （更新状态 伴随动画 重绘界面）
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mMainContent) {
                mMainLeft = left;
            } else {
                mMainLeft += dx;
            }
            //进行修正防止越界
            mMainLeft = fixLeft(mMainLeft);
            if (changedView == mLeftContent) {
                mLeftContent.layout(0, 0, mWidth, mHeight);
                mMainContent.layout(mMainLeft, 0, mMainLeft + mWidth, mHeight);
            }

            dispatchDragEvent(mMainLeft);

            Log.d(TAG, "clampViewPositionHorizontal: mMainLeft = " + mMainLeft);
            //为了兼容2.3版本  2.3版本的此方法没有进行重绘操作，造成无法移动
            invalidate();

        }

        //手指释放的时候触发回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
//            Log.d(TAG, "onViewReleased: xvel :" + xvel + "yvel:" + yvel);

            //释放控件时候操作
            if (xvel == 0 && mMainLeft > mRange / 2 || xvel > 0) {
                open();
                mMainLeft = mRange;
            } else {
                close();
                mMainLeft = 0;
            }
//            Log.d(TAG, "onViewReleased: xvel =" + xvel);
            invalidate();
        }
    };

    private void dispatchDragEvent(int mainLeft) {

        float persent = mainLeft * 1.0f / mRange;

//左面板：伴随动画：缩放平移透明度动画
        //缩放动画
        mLeftContent.setScaleX(persent * 0.3f + 0.7f);
        mLeftContent.setScaleY(persent * 0.3f + 0.7f);

        //平移动画
        ViewHelper.setTranslationX(mLeftContent,evaluate(persent,-mWidth/2.0f,0));
        //透明度变化
        ViewHelper.setAlpha(mLeftContent,evaluate(persent,0.5f,1.0f));

//主面板动画
        ViewHelper.setScaleX(mMainContent,evaluate(persent,1.0,0.75f));
        ViewHelper.setScaleY(mMainContent,evaluate(persent,1.0,0.75f));
        //透明度变化
        ViewHelper.setAlpha(mLeftContent,evaluate(persent,1.0f,0.8f));

    }

    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    private void close() {
        close(true);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //2.持续平滑动画 ，如果返回true，动画还需继续执行
        if (mDrager.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void close(boolean isSmooth) {
        int finalLeft = 0;
        if (isSmooth) {
            //1.触发一个平滑动画
            mMainContent.scrollTo(finalLeft, 0);
            if (mDrager.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
                //返回true代表还没有移动到指定的位置
                //参数传入this（child所在的viewgroup）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mMainContent.layout(finalLeft, 0, mRange + mWidth, mHeight);

        }
    }

    private void open(boolean isSmooth) {
        int finalLeft = mRange;
        if (isSmooth) {
            //1.触发一个平滑动画
            mMainContent.scrollTo(finalLeft, 0);
            if (mDrager.smoothSlideViewTo(mMainContent, finalLeft, 0)) {
                //返回true代表还没有移动到指定的位置
                //参数传入this（child所在的viewgroup）

                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mMainContent.layout(finalLeft, 0, mRange + mWidth, mHeight);

        }
    }

    private void open() {
        open(true);
    }

    private int fixLeft(int left) {
        if (left > mRange) {
            left = mRange;
        } else if (left < 0) {
            left = 0;
        }
        return left;
    }


    //当view中所有子控件均被映射成xml后触发
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftContent = (View) getChildAt(0);
        mMainContent = (View) getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return mDrager.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDrager.processTouchEvent(event);
        return true;
    }

    //视图尺寸发生变化时候调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mRange = (int) (mWidth * 0.6f);
    }
}

