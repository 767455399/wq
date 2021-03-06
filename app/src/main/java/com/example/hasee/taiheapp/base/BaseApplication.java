package com.example.hasee.taiheapp.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

/**
 * Created by wangqing on 2017/12/15.
 */

public class BaseApplication extends MultiDexApplication {

    private static Context applicationContext;



    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        LitePal.initialize(applicationContext);
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