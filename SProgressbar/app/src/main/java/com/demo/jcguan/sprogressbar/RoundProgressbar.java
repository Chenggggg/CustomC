package com.demo.jcguan.sprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by jcguan on 2016/8/4.
 */
public class RoundProgressbar extends HorizantalProgressbar {

    private Paint mPaint;
    private int mRadius = dp2px(30);
    private int mMaxPaintWidth;


    public RoundProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressbar(Context context) {
        this(context, null);
    }

    public RoundProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeigjt = (int) (mUnReachHeight * 2.5f);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgressbar);
        mRadius = (int) typedArray.getDimension(R.styleable.RoundProgressbar_radius, mRadius);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMaxPaintWidth = Math.min(mReachHeigjt,mUnReachHeight);
        //默认四个padding一致
        int expectWidth = mRadius * 2 + mMaxPaintWidth * 2 + getPaddingLeft()+getPaddingRight();

        int width = resolveSize(expectWidth,widthMeasureSpec);
        int height = resolveSize(expectWidth,heightMeasureSpec);

        int readwidth = Math.min(width,height);
        mRadius = (readwidth - getPaddingRight()-getPaddingLeft() - mMaxPaintWidth)/2;

        setMeasuredDimension(readwidth,readwidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.ascent() + mPaint.descent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth/2 ,getPaddingTop()+ mMaxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);
        //draw unreach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

        //draw reach
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeigjt);

        float sweepAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),0,sweepAngle,false,mPaint);

        //draw text
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        canvas.drawText(text,mRadius-textWidth/2,mRadius-textHeight/2,mPaint);
        canvas.restore();
    }
}
