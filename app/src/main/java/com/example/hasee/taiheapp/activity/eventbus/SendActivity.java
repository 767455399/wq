package com.example.hasee.taiheapp.activity.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.modle.LianLianPayModle;

import org.greenrobot.eventbus.EventBus;

public class SendActivity extends AppCompatActivity {
    private EditText editText;
    private Button sendButton;
    private LianLianPayModle lianLianPayModle=new LianLianPayModle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        initView();
    }

    private void initView() {
        editText=(EditText)findViewById(R.id.editText);
        sendButton=(Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busi_partner=editText.getText().toString();
                if(!TextUtils.isEmpty(busi_partner)){
                    lianLianPayModle.busi_partner=busi_partner;
                    lianLianPayModle.no_order=123;
                    EventBus.getDefault().post(lianLianPayModle);
                    finish();
                }else{
                    Toast.makeText(SendActivity.this,"请输入要发送的内容",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
