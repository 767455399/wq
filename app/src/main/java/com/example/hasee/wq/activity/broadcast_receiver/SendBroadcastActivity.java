package com.example.hasee.wq.activity.broadcast_receiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.wq.R;
import com.example.hasee.wq.base.BaseActivity;

public class SendBroadcastActivity extends BaseActivity {
    private TextView sendTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_send_broadcast);
        sendTextView = f(R.id.sendTextView);
        sendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.wq.broadcasttest.MY_BROADCAST");
                intent.putExtra("wq","王清");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    public void initData() {

    }
}
