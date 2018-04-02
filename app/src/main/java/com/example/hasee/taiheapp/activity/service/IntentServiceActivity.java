package com.example.hasee.taiheapp.activity.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.tairanchina.taiheapp.MyIntentService;

public class IntentServiceActivity extends BaseActivity {
    private TextView startServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_intent_service);
        startServices = f(R.id.startService);
        startServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService=new Intent(IntentServiceActivity.this, MyIntentService.class);
                startService(intentService);
            }
        });
    }

    @Override
    public void initData() {

    }
}
