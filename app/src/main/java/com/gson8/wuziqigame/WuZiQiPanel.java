package com.gson8.wuziqigame;

/*
 * WuZiQiGame making by Syusuke/琴声悠扬 on 2016/4/10
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.wuziqigame.WuZiQiPanel
 * Description: null
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WuZiQiPanel extends View
{
    private int mPanelWidth;
    private float mLineHeigth;

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    private float radioPieceLineHeight = 3 * 1.0f / 4;

    private boolean mIsGameOver;
    private boolean isFristPlay = true;

    /**
     * 到白下载了 或者白先下
     */
    private boolean mIsWhiteWinner = true;

    private Paint mPaint = new Paint();

    private int mLineSize = 1;
    private int mLineColor = 0x88000000;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private int MAN_LINE = 10;
    private static int MAX_COUNT_IN_LINE = 5;


    public static int getMaxCountInLine()
    {
        return MAX_COUNT_IN_LINE;
    }

    private OnGameOverListener mOnGameOverListener;
    private OnWhoPlayChessListener mOnWhoPlayChessListener;

    public void setOnGameOverListener(OnGameOverListener mOnGameOverListener)
    {
        this.mOnGameOverListener = mOnGameOverListener;
    }

    public void setOnWhoPlayChessListener(OnWhoPlayChessListener mOnWhoPlayChessListener)
    {
        this.mOnWhoPlayChessListener = mOnWhoPlayChessListener;
    }

    public WuZiQiPanel(Context context)
    {
        this(context, null);
    }

    public WuZiQiPanel(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public WuZiQiPanel(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WuZiQiPanel);
        isFristPlay = ta.getBoolean(R.styleable.WuZiQiPanel_firstPlay, mIsWhiteWinner);
        MAN_LINE = ta.getInteger(R.styleable.WuZiQiPanel_chessBoardLineCount, MAN_LINE);
        MAX_COUNT_IN_LINE = ta.getInteger(R.styleable.WuZiQiPanel_chessCount, MAX_COUNT_IN_LINE);
        mLineColor = ta.getColor(R.styleable.WuZiQiPanel_lineColor, mLineColor);
        mLineSize = ta.getDimensionPixelSize(R.styleable.WuZiQiPanel_lineSize, mLineSize);

        mWhitePiece = BitmapFactory.decodeResource(getResources(),
                ta.getResourceId(R.styleable.WuZiQiPanel_whiteRes, R.drawable.stone_w2));
        mBlackPiece = BitmapFactory.decodeResource(getResources(),
                ta.getResourceId(R.styleable.WuZiQiPanel_blackRes, R.drawable.stone_b1));
        ta.recycle();
        mIsWhiteWinner = isFristPlay;

        init();
    }


    private void init()
    {
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineSize);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heigthSize);

        if(widthMode == MeasureSpec.UNSPECIFIED)
        {
            width = heigthSize;
        }
        else if(heigthMode == MeasureSpec.UNSPECIFIED)
        {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeigth = mPanelWidth * 1.0f / MAN_LINE;


        int picecWidth = (int)(mLineHeigth * radioPieceLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, picecWidth, picecWidth, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, picecWidth, picecWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        drawBoard(canvas);

        drawPiece(canvas);

        if(mOnWhoPlayChessListener != null)
        {
            mOnWhoPlayChessListener.onWhoPlay(mIsWhiteWinner);
        }

        checkGameOver();

        checkHe();
    }

    private void checkHe()
    {
        if(mBlackArray.size() + mWhiteArray.size() >= MAN_LINE * MAN_LINE)
        {
            if(mOnGameOverListener != null)
            {
                mOnGameOverListener.onGameTie();
            }
        }
    }

    private void checkGameOver()
    {
        boolean whitewin = checkFiveWin(mWhiteArray);
        boolean blackwin = checkFiveWin(mBlackArray);

        if(whitewin || blackwin)
        {
            mIsGameOver = true;
            mIsWhiteWinner = whitewin;

            if(mOnGameOverListener != null)
            {
                mOnGameOverListener.onGameWin(mIsWhiteWinner);
            }
        }

    }

    private boolean checkFiveWin(List<Point> points)
    {
        for(Point point : points)
        {
            int x = point.x;
            int y = point.y;

            boolean win = WuZiQiUtil.checkHorizontal(x, y, points);
            if(win)
                return true;
            win = WuZiQiUtil.checkVertical(x, y, points);
            if(win)
                return true;
            win = WuZiQiUtil.checkLeftDiagonal(x, y, points);
            if(win)
                return true;
            win = WuZiQiUtil.checkRightDiagonal(x, y, points);
            if(win)
                return true;

        }

        return false;
    }


    private void drawPiece(Canvas canvas)
    {
        for(int i = 0, size = mWhiteArray.size(); i < size; i++)
        {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - radioPieceLineHeight) / 2) * mLineHeigth,
                    (whitePoint.y + (1 - radioPieceLineHeight) / 2) * mLineHeigth, null);

        }

        for(int i = 0, size = mBlackArray.size(); i < size; i++)
        {
            Point whitePoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (whitePoint.x + (1 - radioPieceLineHeight) / 2) * mLineHeigth,
                    (whitePoint.y + (1 - radioPieceLineHeight) / 2) * mLineHeigth, null);

        }
    }

    private void drawBoard(Canvas canvas)
    {
        int w = mPanelWidth;
        float lineHeiht = mLineHeigth;

        for(int i = 0; i < MAN_LINE; i++)
        {
            int startX = (int)(lineHeiht / 2);
            int endX = (int)(w - lineHeiht / 2);

            int y = (int)((0.5 + i) * lineHeiht);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(mIsGameOver)
            return false;


        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP)
        {
            int x = (int)event.getX();
            int y = (int)event.getY();

            Point p = getValidPoint(x, y);

            if(mWhiteArray.contains(p) || mBlackArray.contains(p))
            {
                return false;
            }

            if(mIsWhiteWinner)
            {
                mWhiteArray.add(p);
            }
            else
            {
                mBlackArray.add(p);
            }
            invalidate();
            mIsWhiteWinner = !mIsWhiteWinner;
        }

        return true;
    }

    private Point getValidPoint(int x, int y)
    {
        return new Point((int)(x / mLineHeigth), (int)(y / mLineHeigth));
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instanceGameOvew";
    private static final String INSTANCE_WHITE_ARRAR = "instance_WA";
    private static final String INSTANCE_BLACK_ARRAR = "instance_BA";


    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAR, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAR, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if(state instanceof Bundle)
        {
            Bundle bundle = (Bundle)state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAR);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAR);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }


    public void reStart()
    {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsWhiteWinner = false;
        mIsGameOver = false;
        mIsWhiteWinner = isFristPlay;
        invalidate();
    }
}


