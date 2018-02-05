package com.example.hasee.wq.activity.startup_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hasee.wq.R;

/**
 * Created by wangqing on 2017/12/26.
 */

public class SingleTopActivity extends AppCompatActivity {
    private Button singleTopButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);
        initView();
    }

    private void initView() {
        singleTopButton = (Button) findViewById(R.id.singleTopButton);
        singleTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleTopActivity.this,"跳转",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(SingleTopActivity.this,SingleTaskActivity.class);
                startActivity(intent);

            }
        });
    }
}
