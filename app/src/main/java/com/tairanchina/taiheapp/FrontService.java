package com.tairanchina.taiheapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hasee.taiheapp.activity.MainActivity;

/**
 * Created by wangqing on 2018/4/2.
 * 前台服务，一直保持运行状态，不会由于系统内存不足而被回收。
 */

public class FrontService extends Service {
    private static final String TAG = "FrontService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("这是一个前台服务")
                .setContentText("前台服务的内容")
                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.error)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.error))
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        Log.d(TAG,"MyService.onCreate&&&&&&&&&&&&&&&&&&&&&&");
    }
}
