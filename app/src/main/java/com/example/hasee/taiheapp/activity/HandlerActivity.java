package com.example.hasee.taiheapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.tools.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandlerActivity extends AppCompatActivity {
    private String path="https://oapi.dingtalk.com/robot/send?access_token=455c76d858fd025a3f94464f9fc6743b918d81c44fd8e3d70cc4adc75d6ddc35";
    private String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"aaa我就是我, 是不一样的烟火\"}}";
    public static final int PERMISSION_CALL_PHONE=0;
    private TextView textView;
    private int i = 0;
    private Button button;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(String.valueOf(msg.arg1));
//            requestPermission();
            handler.postDelayed(runnable, 1000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
            }
        });
        new Thread(runnable).start();
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(5000);
//                    Message message=new Message();
//                    message.arg1=123;
//                    message.obj="wangqing";
//                    handler.sendMessage(message);
//                    Thread.sleep(5000);
//                    Message message1=new Message();
//                    message1.obj="王清";
//                    handler.sendMessage(message1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                request(path,textMsg);
                Thread.sleep(1000);
                i++;
                Message message = new Message();
                message.arg1 = i;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    @SuppressLint("MissingPermission")
    private void callPhone(String phone) {
//        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        startActivity(intent);


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//            startActivity(intent);
//            return;
//        }

    }


    public static String request(String urlPath, String Json) {
        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("hlhupload", "doJsonPost: conn"+conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
                ToastUtil.showNormalToast("1111");
            }else{
                ToastUtil.showNormalToast("2222");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showNormalToast("3333");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ToastUtil.showNormalToast("4444");
        }
        return result;
    }

    private void requestPermission() {
        /**
         * 如果权限未被允许
         */
        if (ContextCompat.checkSelfPermission(HandlerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CALL_PHONE);
        }else{
            callPhone("18667906808");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_CALL_PHONE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callPhone("18667906808");
            }else{
                Toast.makeText(this,"申请权限失败",Toast.LENGTH_SHORT).show();
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
