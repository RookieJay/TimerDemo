package com.zjc.timerdemo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TIME = "time";
    private TextView mTvTime;
    private Button mBtStart;
    private Button mBtStop;
    private Button mBtToAlarm;
    private Button mBtToTimerTask;

    private String timeStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        mBtToAlarm.setVisibility(View.GONE);
        mBtToTimerTask = findViewById(R.id.bt_to_timer_task);
        mTvTime.setText("AlarmManager实现计时器");
    }

    private void initEvent() {
        mBtToTimerTask.setOnClickListener(this);
        mBtStart.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
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
            case R.id.bt_to_timer_task:
                swithToTimerTaskPage();
                break;
            default:
                break;
        }
    }

    private void swithToTimerTaskPage() {
        startActivity(new Intent(this, TimerTaskActivity.class));
    }

    private void stopTimerLoop() {

    }

    @SuppressLint("ShortAlarm")
    private void startTimerLoop() {
        Intent intent = new Intent(AlarmManagerActivity.this, MyBroadcastReceiver.class);
        intent.setAction("flag");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmManagerActivity.this,
                0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                System.currentTimeMillis(), 2 * 1000, pendingIntent);
        sendBroadcast(intent);
        Toast.makeText(AlarmManagerActivity.this, "两秒后开始计时",Toast.LENGTH_LONG).show();
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver{


        /**
        * 一直不能跳到onReceive()方法里面。
         * 解决方案：
         1,如果是静态广播注册方式、广播作为内部类来使用：广播内部类声明为static类型。
         2,如果是非静态广播注册方式：广播必须在类中注册（调用registerReceiver()方法）、注销（调用unregisterReceiver()方法）。
        * */
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                if (intent.getAction().equals("flag")) {
                    Toast.makeText(context, "开始计时!", Toast.LENGTH_SHORT).show();
//                    mTvTime.setText("11111");
                }
            }
        }
    }
}
