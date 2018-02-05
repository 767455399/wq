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

public class OtherActivity extends AppCompatActivity {
    private Button otherButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        init();
    }

    private void init() {
        otherButton = (Button) findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherActivity.this, SingleTopActivity.class));
            }
        });
    }
}
