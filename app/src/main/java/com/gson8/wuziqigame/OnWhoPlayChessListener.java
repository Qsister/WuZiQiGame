package com.gson8.wuziqigame;

/*
 * WuZiQiGame making by Syusuke/琴声悠扬 on 2016/4/10
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.wuziqigame.OnWhoPlayChessListener
 * Description: null
 */

public interface OnWhoPlayChessListener
{
    /**
     * 轮到谁下棋了.
     *
     * @param mIsWhitePlay true白棋下;false 黑棋下
     */
    void onWhoPlay(boolean mIsWhitePlay);
}
