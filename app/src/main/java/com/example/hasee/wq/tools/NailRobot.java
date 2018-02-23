package com.example.hasee.wq.tools;

import android.text.TextUtils;
import android.util.Log;

import com.example.hasee.wq.modle.NailRobotModle;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by wangqing on 2018/2/23.
 */

public class NailRobot {
    private String path = "https://oapi.dingtalk.com/robot/send?access_token=455c76d858fd025a3f94464f9fc6743b918d81c44fd8e3d70cc4adc75d6ddc35";
    public static void sengMsg(final String urlPath, final String content, final List<String> phoneList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                request(urlPath, content, phoneList);
            }
        }).start();
    }

    public static void request(String urlPath, String content, List<String> phoneList) {
        NailRobotModle nailRobotModle = new NailRobotModle();
        nailRobotModle.msgtype = "text";
        if (!TextUtils.isEmpty(content)) {
            NailRobotModle.TextBean textBean = new NailRobotModle.TextBean();
            textBean.content = content;
            nailRobotModle.text = textBean;
        }
        if (phoneList != null) {
            NailRobotModle.AtBean atBean = new NailRobotModle.AtBean();
            atBean.atMobiles = phoneList;
            nailRobotModle.at = atBean;
        }
        String json = new Gson().toJson(nailRobotModle);

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
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept", "application/json");
            // 往服务器里面发送数据
            if (json != null && !TextUtils.isEmpty(json)) {
                byte[] writebytes = json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("hlhupload", "doJsonPost: conn" + conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
