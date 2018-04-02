package com.example.hasee.taiheapp.activity.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.tairanchina.taiheapp.MyService;


/**
 * 普通服务，也称后台服务，服务系统优先级比较低，当系统内存不足的时候
 */
public class ServiceActivity extends BaseActivity {
    private TextView bindService;
    private TextView unbindService;
    private TextView startWork;
    private TextView stopWork;
    private MyService.DownLoadBinder downLoadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBinder = (MyService.DownLoadBinder) service;
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
        setContentView(R.layout.activity_service);
        bindService=f(R.id.bindService);
        unbindService=f(R.id.unbindService);
        startWork=f(R.id.startWork);
        stopWork=f(R.id.stopWork);
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindIntent=new Intent(ServiceActivity.this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务；
            }
        });

        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unbindIntent=new Intent(ServiceActivity.this,MyService.class);
                unbindService(connection);
            }
        });

        startWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadBinder.startDownLoad();
            }
        });

        stopWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadBinder.stopDownLoad();
            }
        });
    }

    @Override
    public void initData() {

    }
}
