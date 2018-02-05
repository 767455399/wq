package com.example.hasee.wq.activity.startup_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hasee.wq.R;

/**
 * Created by wangqing on 2017/12/26.
 */

public class StandardActivity extends AppCompatActivity {
    private Button standardButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        init();
    }

    private void init() {
        standardButton=(Button)findViewById(R.id.standardButton);
        standardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(StandardActivity.this,SingleTopActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}
