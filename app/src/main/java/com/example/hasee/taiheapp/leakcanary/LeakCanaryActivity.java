package com.example.hasee.taiheapp.leakcanary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.activity.LoginActivity;


/**
 * MainActivity 中点击按钮后，’test()’ 方法内部匿名内部类执行了耗时任务，并且同时 finish() 掉 MainActivity，但是此匿名内部类依然在运行任务，
 * 并且隐式的持有 MainActivity 引用，导致 MainActivity 不能及时被 GC 回收，导致内存泄露。
 */

public class LeakCanaryActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
        init();
    }

    private void init() {
        textView=(TextView)findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
                finish();
                startActivity(new Intent(LeakCanaryActivity.this, LoginActivity.class));
            }
        });
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
