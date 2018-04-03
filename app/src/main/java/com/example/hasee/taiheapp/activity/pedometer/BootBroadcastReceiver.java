package com.example.hasee.taiheapp.activity.pedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wangqing on 2018/4/3.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BootBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.d(TAG, "onReceive: 收到开机广播了************");
        }
        if(intent.getAction().equals("android.intent.action.DATE_CHANGED")){
            Log.d(TAG, "onReceive: 日期变化了");
        }

    }
}
