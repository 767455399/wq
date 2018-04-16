package com.example.hasee.taiheapp.activity.pedometer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.tairanchina.taiheapp.BindService;
import com.tairanchina.taiheapp.StepService;

public class StepActivity extends BaseActivity {
    private TextView step;
    private TextView startStep;
    private static SensorManager sensorManager;
    private SharedPreferences sp;
    private StepBroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private Intent stepIntent;

    /**
     * 第三方计步
     */
    private BindService bindService;
    private boolean isBind;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                step.setText(msg.arg1 + "");
            }
        }
    };

    /**
     * 第三方计步
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BindService.LcBinder lcBinder = (BindService.LcBinder) service;
            bindService = lcBinder.getService();
            bindService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    //当前接收到stepCount数据，就是最新的步数
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg1 = stepCount;
                    handler.sendMessage(message);
                    Log.i("MainActivity—updateUi","当前步数"+stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_step);
        step = f(R.id.step);
        startStep = f(R.id.startStep);
        /**
         * 开启服务
         */
        if (stepIntent == null) {
            stepIntent = new Intent(StepActivity.this, StepService.class);
            bindService(stepIntent, serviceConnection, BIND_AUTO_CREATE);
        }
        /**
         * 动态注册广播
         */
        receiver = new StepBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.wq.STEP_COUNT");

        /**
         * 第三方计步
         */
        Intent intent = new Intent(StepActivity.this, BindService.class);
        isBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
//        showData();
    }

    private void showData() {
        sp = getSharedPreferences("step", MODE_PRIVATE);
        String account = String.valueOf(sp.getInt("steps", 0));
        step.setText(account);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

        /**
         * 第三方计步
         */
        if(isBind){
            this.unbindService(serviceConnection);
        }

    }

    @Override
    public void initData() {
        startStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    /**
     * 接收广播
     */
    class StepBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.wq.STEP_COUNT")) {
                int step1 = intent.getIntExtra("step", 0);
                step.setText("步数" + step1);
            }
        }
    }

    /**
     * 开机提醒
     */
    class BootBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
