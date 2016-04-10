package com.gson8.wuziqigame;

/*
 * WuZiQiGame making by Syusuke/琴声悠扬 on 2016/4/10
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.wuziqigame.OnGameOverListener
 * Description: null
 */

public interface OnGameOverListener
{
    /**
     * @param win true,白赢; false,黑赢
     */
    void onGameWin(boolean win);

    /**
     * 平局
     */
    void onGameTie();
}
