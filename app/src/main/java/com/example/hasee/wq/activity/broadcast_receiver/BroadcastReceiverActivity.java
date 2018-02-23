package com.example.hasee.wq.activity.broadcast_receiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.wq.R;
import com.example.hasee.wq.base.BaseActivity;

public class BroadcastReceiverActivity extends BaseActivity {
    private TextView receiveTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_broadcast_receiver);
        receiveTextView=f(R.id.receiveTextView);
        receiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BroadcastReceiverActivity.this,SendBroadcastActivity.class));
            }
        });
    }

    @Override
    public void initData() {

    }


}


