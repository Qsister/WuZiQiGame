package com.gson8.blackwhitechess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameiPanel extends View
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
    private boolean mIsWhiteWinner = false;

    private Paint mPaint = new Paint();

    private int[][] mChess = new int[8][8];


    private static int BLACK = 1;
    private static int WHITE = -1;

    private int mLineColor = 0xff000000;

    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();
    private float Half_Line_Height;
    private int MAN_LINE = 9;

    private int TOP_LEADDING = 8;


    public GameiPanel(Context context)
    {
        this(context, null);
    }

    public GameiPanel(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GameiPanel(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);


        mChess[3][3] = WHITE;
        mChess[4][4] = WHITE;

        mChess[3][4] = BLACK;
        mChess[4][3] = BLACK;

        init();
    }


    private Paint mCirClePaint = new Paint();

    private void init()
    {
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirClePaint.setColor(Color.BLACK);
        mCirClePaint.setAntiAlias(true);
        mCirClePaint.setStyle(Paint.Style.FILL);


        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
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
        mLineHeigth = (mPanelWidth - 2 * TOP_LEADDING) * 1.0f / (MAN_LINE - 1);
        Half_Line_Height = mLineHeigth / 2;

        Log.e("TAGGGGG", "onSizeChanged: " + mLineHeigth + "  " + Half_Line_Height);

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

        //checkGameOver();

        //checkHe();
    }

    private void checkHe()
    {

    }

    private void checkGameOver()
    {
        boolean whitewin = checkFiveWin(mWhiteArray);
        boolean blackwin = checkFiveWin(mBlackArray);

    }

    private boolean checkFiveWin(List<Point> points)
    {
        return false;
    }


    private void drawPiece(Canvas canvas)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(mChess[i][j] == WHITE)
                {
                    float centerX = i * mLineHeigth + Half_Line_Height + TOP_LEADDING;
                    float cneterY = j * mLineHeigth + Half_Line_Height + TOP_LEADDING;

                    //                    canvas.drawBitmap(mWhitePiece, centerX, cneterY, null);
                    mCirClePaint.setColor(Color.WHITE);
                    canvas.drawCircle(centerX, cneterY, Half_Line_Height * radioPieceLineHeight,
                            mCirClePaint);
                    Log.e("drawPicec", "WHITE" + centerX + "   " + cneterY);
                }
                else if(mChess[i][j] == BLACK)
                {

                    float centerX = i * mLineHeigth + Half_Line_Height + TOP_LEADDING;
                    float cneterY = j * mLineHeigth + Half_Line_Height + TOP_LEADDING;

                    //                    canvas.drawBitmap(mBlackPiece, centerX, cneterY, null);
                    mCirClePaint.setColor(Color.BLACK);
                    canvas.drawCircle(centerX, cneterY, Half_Line_Height * radioPieceLineHeight,
                            mCirClePaint);
                    Log.e("drawPicec",
                            "BLACK: i=" + centerX + "   " + cneterY + "   " + mLineHeigth + "   " + Half_Line_Height);
                }
            }
        }

    }

    /**
     * 画棋盘
     *
     * @param canvas
     */
    private void drawBoard(Canvas canvas)
    {
        int w = mPanelWidth;
        float lineHeiht = mLineHeigth;

        Log.e("tagggg", lineHeiht + ".." + mLineHeigth);

        for(int i = 0; i < MAN_LINE; i++)
        {
            int startX = TOP_LEADDING;
            int endX = w - TOP_LEADDING;

            int y = (int)(TOP_LEADDING + i * lineHeiht);

            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
       /* if(mIsGameOver)
            return false;*/

        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP)
        {
            int x = (int)event.getX();
            int y = (int)event.getY();

            Point p = getValidPoint(x, y);

            checkLeft(p);

            System.out.println(x + " " + y);

            if(!chekcIsClick(p))
            {
                return false;
            }

            Log.e("POINT<ONCLI", p.x + " : " + p.y);


            if(mIsWhiteWinner)
            {
                //添加白棋
                mChess[p.x][p.y] = WHITE;
            }
            else
            {
                //添加黑棋
                mChess[p.x][p.y] = BLACK;
            }

            invalidate();

            mIsWhiteWinner = !mIsWhiteWinner;
        }

        return true;
    }

    private boolean chekcIsClick(Point point)
    {
        //判断点下的地方是否有棋子
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(mChess[point.x][point.y] == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private Point getValidPoint(int x, int y)
    {
        int xx = getValidArray((int)((x - TOP_LEADDING) / mLineHeigth));
        int yy = getValidArray((int)((y - TOP_LEADDING) / mLineHeigth));

        return new Point(xx, yy);
    }


    private int getValidArray(int i)
    {
        if(i >= MAN_LINE - 1)
        {
            return i - 2;
        }
        else if(i < 0)
        {
            return 0;
        }
        else
            return i;
    }


/*
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
*/


    public void reStart()
    {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsWhiteWinner = false;
        mIsGameOver = false;
        mIsWhiteWinner = isFristPlay;
        invalidate();
    }


    /**
     * 向左检测是否有相同颜色棋子，如果有且不相邻，改变中间棋子颜色，否则返回NULL
     *
     * @param p
     * @return
     */
    private int[] checkLeft(Point p)
    {
        int x = p.x;
        int y = p.y;

        Log.e("checkLeft", x + " : " + y);

        int r = -2;
        int i;
        //向左
        for(i = x - 1; i >= 0; i--)
        {
            if(mChess[i][y] != 1 && mChess[i][y] != -1)
            {
                continue;
            }
            Log.e("checkLeft", "checkLeft: " + i + " . " + mChess[i][y] + " " + mChess[x][y]);

            if(mChess[i][y] == mChess[x][y])
            {
                r = i;
                break;
            }
        }
        System.out.println(r);

        if(r != -2 && mChess[x - 1][y] != mChess[i][y])
        {
            Log.e("checkLeft", r + " :------------- " + y + ".");
            return new int[]{ r, y };
        }
        else
        {
            Log.e("checkLeft","不能");
            //向左不能.
            return null;
        }
    }


}


