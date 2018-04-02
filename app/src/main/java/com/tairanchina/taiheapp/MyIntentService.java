package com.tairanchina.taiheapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");
        Log.d(TAG,"MyIntentService.MyIntentService&&&&&&&&&&&&&&&&&&&&&&");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyIntentService.onCreate&&&&&&&&&&&&&&&&&&&&&&");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"MyIntentService.onHandleIntent&&&&&&&&&&&&&&&&&&&&&&");

    }


}
