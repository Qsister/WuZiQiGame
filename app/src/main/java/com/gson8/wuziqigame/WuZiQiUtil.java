package com.gson8.wuziqigame;

/*
 * WuZiQiGame making by Syusuke/琴声悠扬 on 2016/4/10
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.wuziqigame.WuZiQiUtil
 * Description: null
 */

import android.graphics.Point;

import java.util.List;

public class WuZiQiUtil
{
    /**
     * 横向检测是否WIN ,
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkHorizontal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = WuZiQiPanel.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }


    /**
     * 纵向检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkVertical(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = WuZiQiPanel.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }

    /**
     * 左斜检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkLeftDiagonal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = WuZiQiPanel.getMaxCountInLine();
        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }

    /**
     * 右斜检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    public static boolean checkRightDiagonal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = WuZiQiPanel.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }
}
