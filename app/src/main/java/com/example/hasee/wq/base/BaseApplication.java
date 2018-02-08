package com.example.hasee.wq.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wangqing on 2017/12/15.
 */

public class BaseApplication extends MultiDexApplication {

    private static Context applicationContext;



    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }


    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context context) {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

}