package com.example.hasee.taiheapp.activity.startactivityforresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;

public class StartOneActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_one);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StartOneActivity.this, StartTwoActivity.class);
                intent.putExtra("data", "这是界面一给界面二的数据");
                startActivityForResult(intent, 99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (99 == requestCode) {
            if (RESULT_OK == resultCode) {
                if (!TextUtils.isEmpty(data.getStringExtra("data_return")))
                    textView.setText(data.getStringExtra("data_return"));
            }
        }
    }
}
