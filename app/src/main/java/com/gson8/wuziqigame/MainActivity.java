package com.gson8.wuziqigame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    TextView tvl;
    WuZiQiPanel mWuZiQiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvl = (TextView)findViewById(R.id.tvl);

        mWuZiQiPanel = (WuZiQiPanel)findViewById(R.id.wzq);
        mWuZiQiPanel.setOnGameOverListener(new OnGameOverListener()
        {
            /**
             *
             * @param win true,白赢; false,黑赢
             */
            @Override
            public void onGameWin(boolean win)
            {
                Toast.makeText(MainActivity.this, win ? "白" : "黑", Toast.LENGTH_SHORT).show();
            }

            /**
             * 平局调用
             */
            @Override
            public void onGameTie()
            {

            }
        });

        mWuZiQiPanel.setOnWhoPlayChessListener(new OnWhoPlayChessListener()
        {
            @Override
            public void onWhoPlay(boolean mIsWhitePlay)
            {
                if(mIsWhitePlay)
                {
                    tvl.setText("白下");
                }
                else
                {
                    tvl.setText("黑下");
                }
            }
        });

    }
}
