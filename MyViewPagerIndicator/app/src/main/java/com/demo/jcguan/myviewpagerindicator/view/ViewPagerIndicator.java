package com.demo.jcguan.myviewpagerindicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.jcguan.myviewpagerindicator.R;

import java.util.List;

/**
 * Created by jcguan on 2016/8/2.
 */
public class ViewPagerIndicator extends LinearLayout {
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    private static final int COLOR_TEXT_HEIGHT_lIGHT = 0xFFFFFFFF;
    //画笔
    private Paint mPaint;
    //构造三角形的类
    private Path mPath;
    //三角形的宽
    private int mTriangleWidth;
    //三角形的高
    private int mTriangleHeigh;
    //三角形宽占条目比例
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;
    //三角形最大宽度
    private final int RADIO_TRIANGLE_WIDTH_MAX = (int) (getScreenWidth()/3*RADIO_TRIANGLE_WIDTH);
    //初始化偏移量
    private int mInitTranslationX;
    //偏移量
    private int mTranslationX;
    //总共
    private int mTabVisibleCount;
    //默认
    private static final int COUNT_DEFAULT = 4;
    private List<String> mTitles;
    //设置关联的viewpager
    public ViewPager mViewPager;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取可见Tab的数量
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT);


        if (mTabVisibleCount <= 0) {
            mTabVisibleCount = COUNT_DEFAULT;
        }
        //释放
        a.recycle();

        //初始化画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔颜色
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        //连接线展示效果   圆角
        mPaint.setPathEffect(new CornerPathEffect(3));

    }


    //绘List<String>制VIew本身的内容，通过调用View.onDraw(canvas)函数实现
    //绘制自己的孩子通过dispatchDraw（canvas）实现
    @Override
    protected void dispatchDraw(Canvas canvas) {

        //三角形的绘制在此方法中
        canvas.save();
        //移动画布
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();
        super.dispatchDraw(canvas);
    }

    //适用于需要知道控件宽度，或者根据控件宽高去设置一些宽的或者高度
    //因为只要控件的宽高发生变化时候都会回调这个方法
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //三角形底边的宽度
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mTriangleWidth = Math.min(RADIO_TRIANGLE_WIDTH_MAX,mTriangleWidth);
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        //初始化三角形
        initTriangle();

    }

    //当xml文件加载完成后回调
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();
        if (cCount == 0) {
            return;
        }
        //判断子view个数及设置属性
        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }

        setItemClickEvent();
    }

    /*
    获取屏幕的宽度
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outmetris = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outmetris);
        return outmetris.widthPixels;
    }

    private void initTriangle() {

        mTriangleHeigh = mTriangleWidth / 2;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeigh);
        mPath.close();

    }


    //指示器跟随手指滑动
    public void scroll(int position, float positionOffset) {
        //总的偏移量 = tabWidth*(positionOffset + position)
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) (tabWidth * (positionOffset + position));

        if (position >= mTabVisibleCount - 2 && positionOffset > 0 && getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {

                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * positionOffset), 0);
            } else {
                this.scrollTo(position * tabWidth + (int) (tabWidth * positionOffset), 0);
            }
        }

        invalidate();
    }

    //动态设置tab标签
    public void setTabItemTitle(List<String> titles) {

        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            mTitles = titles;
            for (String title : titles) {
                this.addView(generateTextView(title));
            }
        }
        setItemClickEvent();
    }


    //动态设置可见的tabCount
    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
    }


    //根据title创建tab
    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        return tv;
    }


    public void setViewPager(ViewPager viewPager, int pos) {
        mViewPager = viewPager;


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                scroll(position, positionOffset);
                if (mListener != null) {
                    mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }
                resetColor();
                setHeightLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });

        mViewPager.setCurrentItem(pos);
        setHeightLightTextView(pos);
    }


    //暴露回掉接口
    public interface PageOnChangeListener {

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    public PageOnChangeListener mListener;

    public void setPageOnChangeListener(PageOnChangeListener listener) {
        this.mListener = listener;
    }


    //高亮某个tab的文本
    public void setHeightLightTextView(int pos) {
        View view = getChildAt(pos);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HEIGHT_lIGHT);
        }
    }

    //重置文本颜色
    private void resetColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }


    //设置tab点击事件
    private void setItemClickEvent() {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

}
