package com.demo.jcguan.sprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by jcguan on 2016/8/4.
 */
public class HorizantalProgressbar extends ProgressBar {

    //编写出行的默认值
    protected static final int DEFAULT_TEXT_SIZE = 16;//sp
    protected static final int DEFAULT_TEXT_COLOR = 0XFFFC0D1;//sp
    protected static final int DEFAULT_COLOR_UNREACH = 0XFFD3D6DA;//sp
    protected static final int DEFAULT_HEIGHT_UNREACH = 2;//sp
    protected static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;//sp
    protected static final int DEFAULT_HEIGHT_REACH = 2;//sp
    protected static final int DEFAULT_TEXT_OFFSET = 10;//sp

    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mReachHeigjt = dp2px(DEFAULT_HEIGHT_REACH);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint = new Paint();

    protected int mRealWidth;


    public HorizantalProgressbar(Context context) {  //直接new对象时候调用
        this(context, null);
    }

    public HorizantalProgressbar(Context context, AttributeSet attrs) {  //在布局文件中调用
        this(context, attrs, 0);
    }

    public HorizantalProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        obtainStyledAttrs(attrs);

    }

    //获取自定义属性
    protected void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizantalProgressbar);
        mTextSize = (int) ta.getDimension(R.styleable.HorizantalProgressbar_progress_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.HorizantalProgressbar_progress_text_color, mTextColor);
        mReachColor = ta.getColor(R.styleable.HorizantalProgressbar_progress_reached_color, mReachColor);
        mReachHeigjt = (int) ta.getDimension(R.styleable.HorizantalProgressbar_progress_reach_heigjt, mReachHeigjt);
        mUnReachColor = ta.getColor(R.styleable.HorizantalProgressbar_progress_unreach_color, mUnReachColor);
        mUnReachHeight = (int) ta.getDimension(R.styleable.HorizantalProgressbar_progress_unreach_height, mUnReachHeight);
        mTextOffset = (int) ta.getDimension(R.styleable.HorizantalProgressbar_progress_text_offset, mTextOffset);
        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);
        //自定义属性，需要判断是否用户有设置
        int height = measureHeight(heightMeasureSpec);

        //确定view的宽高
        setMeasuredDimension(widthVal, height);
        //setMeasuredDimension方法之后可通过getMeasuredWidth()获取控件高度
        mRealWidth = getMeasuredWidth() - getPaddingBottom() - getPaddingTop() ;

    }

    //测量高度
    protected int measureHeight(int heightMeasureSpec) {

        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            size = result;
        } else {
            //.ascent：是baseline之上至字符最高处的距离    descent：是baseline之下至字符最低处的距离
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            //控件高度，三者最大加上上下边距
            result = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeigjt, mUnReachHeight), Math.abs(textHeight));

            if (mode == MeasureSpec.AT_MOST) {
                //最大高度不能超过父控件的的高度
                result = Math.min(size, result);
            }
        }

        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        //draw reach bar
        String text = getProgress() + "%";
        int textWidth = (int) mPaint.measureText(text);

        boolean noNeedUnRech = false;
        float radio = getProgress() * 1.0f / getMax();
        float progressX = radio * mRealWidth;
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnRech = true;
        }
        float endX = progressX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeigjt);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);

        //draw unreach bar
        if(!noNeedUnRech){
            float start = progressX + mTextOffset/2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    //dp转换pxfangfa
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    //px转换成dp方法
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }


}
