package com.demo.jcguan.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcguan on 2016/8/3.
 */
public class WuziqiPanel extends View {

    private int mPanelWidth;
    //每行的高度
    private float mLineHeight;
    private int MAX_LINE = 10;

    private Paint mPaint = new Paint();

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    //确定棋子大小的比例
    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;
    //存放棋子的数组
    private List<Point> mWhiteArray = new ArrayList<>();
    private List<Point> mBlackArray = new ArrayList<>();
    //白棋先手或者当前轮到白棋
    private boolean mIsWhite = true;
    private static final String TAG = "WuziqiPanel";
    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;


    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);
        init();
    }

    //初始化画笔
    private void init() {
        mPaint.setColor(0x88000000);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //抖动
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        //将图片读取出来
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        //将view的高度和宽度传入，设置子view实际的大小
        setMeasuredDimension(width, width);

    }

    //控件尺寸变化相关操作
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        //设置棋子显示缩放比例尺寸
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mIsGameOver) {
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x, y);
            if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                return true;
            }
            if (mIsWhite) {
                mWhiteArray.add(p);
            } else {
                mBlackArray.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;

            return true;
        }

        return super.onTouchEvent(event);
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }


    //绘制操作
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.d(TAG, "onDraw: 调用了");
        //绘制棋盘
        drawBoard(canvas);
        //绘制棋子
        drawPieces(canvas);
        //检验游戏是否结束
        checkIsGameOver();

    }

    private void checkIsGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);

        if (whiteWin || blackWin) {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkFiveInLine(List<Point> points) {

        for (Point p:points) {
            int x = p.x;
            int y = p.y;
            
            boolean win = checkHorizontal(x,y,points);
        }
        return false;
    }

    //判断xy位置的棋子是否有横向相邻的五个
    private boolean checkHorizontal(int x, int y, List<Point> points) {

        return false;
    }

    //绘制棋子
    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            Log.d(TAG, "drawPieces: mWhiteArray" + mWhiteArray.size());
            canvas.drawBitmap(mWhitePiece, (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
        for (int i = 0, n = mBlackArray.size(); i < n; i++) {
            Point BlackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece, (BlackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, (BlackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight, null);
        }
    }

    //绘制棋盘
    private void drawBoard(Canvas canvas) {

        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }


}
