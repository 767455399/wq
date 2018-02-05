package com.example.hasee.wq.activity.startup_mode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hasee.wq.R;

/**
 * Created by wangqing on 2017/12/26.
 */

public class SingleInstanceActivity extends AppCompatActivity {
    private Button singleInstanceButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        init();
    }

    private void init() {
        singleInstanceButton=(Button)findViewById(R.id.singleInstanceButton);
        singleInstanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
