package com.example.hasee.wq.tools;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wangqing on 2018/2/6.
 */

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
