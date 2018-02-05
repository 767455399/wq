package com.example.hasee.wq.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.wq.R;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;

/**
 * Created by wangqing on 2017/12/21.
 */

public class EventBusActivity extends AppCompatActivity {
    private static final int FRESH=001;
    private TextView sendTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        sendTextView=(TextView)findViewById(R.id.sendTextView);
        sendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        RxBus.get().register(this);
        sendEvent();
    }

    private void sendEvent(){
        RxBus.get().send(FRESH,"刷新数据吧");
    }


    @Subscribe(code = 001)
    public void refresh(String msg){
        sendTextView.setText(msg);
//        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
