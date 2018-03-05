package com.example.hasee.taiheapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.tools.DingTalkUtil;

import java.util.List;

/**
 * 钉钉错误日志报告
 */
public class DingTalkActivity extends BaseActivity {
    private EditText contentEditText,receiverPhoneNoEditText;
    private Button sendButton;
    private String path = "https://oapi.dingtalk.com/robot/send?access_token=455c76d858fd025a3f94464f9fc6743b918d81c44fd8e3d70cc4adc75d6ddc35";
    private String textMsg = "你没有小鸡鸡吗？";
    private String content;
    private List<String> receiverPhoneNoList;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_ding_talk);
        contentEditText=f(R.id.contentEditText);
        sendButton=f(R.id.sendButton);
        receiverPhoneNoEditText=f(R.id.receiverPhoneNoEditText);
    }

    @Override
    public void initData() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    private void send() {
        content=contentEditText.getText().toString();
        phone=receiverPhoneNoEditText.getText().toString();
        if(!TextUtils.isEmpty(phone)){
            receiverPhoneNoList.add(phone);
        }
        if(!TextUtils.isEmpty(content)){
            DingTalkUtil.sengMsg(path,content,receiverPhoneNoList);
        }
    }

}
