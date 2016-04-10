package com.gson8.wuziqigame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener
{
    TextView tvDis;
    WuZiQiPanel mWuZiQiPanel;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        tvDis = (TextView)findViewById(R.id.tv_dis);

        mWuZiQiPanel = (WuZiQiPanel)findViewById(R.id.wzq);

        mToolbar.inflateMenu(R.menu.menu_wu);
        mToolbar.setOnMenuItemClickListener(this);

        mWuZiQiPanel.setOnGameOverListener(new OnGameOverListener()
        {
            /**
             *
             * @param win true,白赢; false,黑赢
             */
            @Override
            public void onGameWin(boolean win)
            {
                //                Toast.makeText(MainActivity.this, win ? "白棋赢了" : "黑棋赢了", Toast.LENGTH_SHORT).show();
                showDia(win ? "白棋赢了" : "黑棋赢了");
            }

            /**
             * 平局调用
             */
            @Override
            public void onGameTie()
            {
                //                Toast.makeText(MainActivity.this, "和了", Toast.LENGTH_SHORT).show();
                showDia("和了");
            }
        });

        mWuZiQiPanel.setOnWhoPlayChessListener(new OnWhoPlayChessListener()
        {
            @Override
            public void onWhoPlay(boolean mIsWhitePlay)
            {
                if(mIsWhitePlay)
                {
                    tvDis.setText("轮到白下");
                }
                else
                {
                    tvDis.setText("轮到黑下");
                }
            }
        });

    }

    private void showDia(String msg)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg + ",是否再来一局");
        builder.setPositiveButton("不要了", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.setNegativeButton("再来一局", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mWuZiQiPanel.reStart();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        if(item.getItemId() == R.id.action_restart)
        {
            mWuZiQiPanel.reStart();
            return true;
        }
        else if(item.getItemId() == R.id.action_pre)
        {
            mWuZiQiPanel.pre();
        }
        return false;
    }
}
