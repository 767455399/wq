package com.example.hasee.taiheapp.activity.startactivityforresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;

public class StartTwoActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_two);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void initView() {
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        Intent intent=getIntent();
        if(!TextUtils.isEmpty(intent.getStringExtra("data"))){
            textView.setText(intent.getStringExtra("data"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("data_return","界面二返回的数据给界面一");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * 这里需要注意的地方，如果界面而必须把数据传递给界面一，那么在用户直接点击物理返回键的时候，
     * 其实是没有处理返回数据的，此时物理返回界面一也就无法获取到界面二的数据，所以这里必须在物理返回事件里面也处理
     * 数据的返回事件
     * TODO 为什么super.onBackPressed();放前面的时候，后面的代码执行了，但是传递数据的功能并未实现，
     * 将super.onBackPressed();放置到后面时传递数据的功能是正常的。
     */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent();
        intent.putExtra("data_return","界面二返回的数据给界面一");
        setResult(RESULT_OK,intent);
        Toast.makeText(StartTwoActivity.this,"执行了",Toast.LENGTH_SHORT).show();
        finish();

    }
}
