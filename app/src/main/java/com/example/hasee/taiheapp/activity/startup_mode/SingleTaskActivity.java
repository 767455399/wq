package com.example.hasee.taiheapp.activity.startup_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;

/**
 * Created by wangqing on 2017/12/26.
 */

public class SingleTaskActivity extends AppCompatActivity {
    private Button singleTaskButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        init();
    }

    private void init() {
        singleTaskButton = (Button) findViewById(R.id.singleTaskButton);
        singleTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleTaskActivity.this,"SingleTaskActivity",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SingleTaskActivity.this, SingleTaskActivity.class));
            }
        });
    }
}
