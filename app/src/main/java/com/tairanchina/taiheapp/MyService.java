package com.tairanchina.taiheapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private DownLoadBinder mbinder=new DownLoadBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyService.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"MyService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return mbinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MyService.onDestroy");
    }

    public class DownLoadBinder extends Binder {

        public void startDownLoad(){
            Log.d(TAG,"MyService.startDownLoad********");
        }

        public void stopDownLoad(){
            Log.d(TAG,"MyService.stopDownLoad*********");
        }

    }
}
