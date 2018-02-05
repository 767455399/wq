package com.example.hasee.wq.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hasee.wq.R;

public class DelayedOperationActivity extends AppCompatActivity {
    private TextView textView;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed_operation);
        textView=(TextView)findViewById(R.id.textView);
        viewDelay();
    }

    /**
     * thread延迟执行
     */
    private void ThreadDelay() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //TODO 下面两行代码互换顺序会出现线程问题
                    textView.setText("thread延迟3s操作");
                    Thread.sleep(3000);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //主线程；

                        }
                    },1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 线程延时操作
     */
    private void handlerDelay() {
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               textView.setText("handler线程延迟2000s");
               ThreadDelay();
           }
       },2000);
    }


    /**
     * 通过控件延迟执行，可以用于延迟刷新
     */
    private void viewDelay() {
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("延迟1s了");
                handlerDelay();
            }
        },1000);
    }
}
