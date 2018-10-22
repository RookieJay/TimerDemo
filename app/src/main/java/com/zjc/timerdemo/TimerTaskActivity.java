package com.zjc.timerdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvTime;
    private Button mBtStart;
    private Button mBtStop;
    private Button mBtToAlarm;
    private Button mBtToTimerTask;

    private String timeStr;

    Timer timer;
    TimerTask task;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mTvTime.setText(timeStr);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        mTvTime = findViewById(R.id.time);
        mBtStart = findViewById(R.id.bt_start);
        mBtStop = findViewById(R.id.bt_stop);
        mBtToAlarm = findViewById(R.id.bt_to_alarm_manager);
        mBtToTimerTask = findViewById(R.id.bt_to_timer_task);
        mBtToTimerTask.setVisibility(View.GONE);
        mTvTime.setText("TimerTask实现计时");
    }

    private void initEvent() {
        mBtStart.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
        mBtToAlarm.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {// 停止timer
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                startTimerLoop();
                break;
            case R.id.bt_stop:
                stopTimerLoop();
                break;
            case R.id.bt_to_alarm_manager:
                switchToAlarmPage();
                break;
            default:
                break;
        }
    }

    private void switchToAlarmPage() {
        startActivity(new Intent(this, AlarmManagerActivity.class));
    }

    private void startTimerLoop() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        timer = new Timer();
        task  = new TimerTask() {
            public void run() {
                timeStr = df.format(new Date());
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        };
        //第二个参数是等待1秒后执行schedule，第三个参数是每隔一秒重复执行一次
        timer.schedule(task, 1000, 1000);
    }

    private void stopTimerLoop() {
        timer.cancel();
    }
}
