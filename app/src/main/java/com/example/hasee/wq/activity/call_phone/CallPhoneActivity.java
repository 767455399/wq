package com.example.hasee.wq.activity.call_phone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.wq.R;
import com.example.hasee.wq.dialog.PermissionDialog;

public class CallPhoneActivity extends AppCompatActivity {
    private static final int CALL_PHONE_PERMISSION = 1;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();

            }

            private void checkPermissions() {
                if (ContextCompat.checkSelfPermission(CallPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    /**
                     * 如果权限未被禁止弹框则继续申请，并告知用途以及不允许权限的弊端。
                     */
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(CallPhoneActivity.this, Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(CallPhoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION);
                    } else {
                        /**
                         * 完全禁止且无法再弹权限申请框，这时弹框提示用户去设置开启权限。
                         */
                        if (CallPhoneActivity.this.hasWindowFocus()) {
                            String content = "权限中开启电话权限";
                            new PermissionDialog(CallPhoneActivity.this, content, new PermissionDialog.Listener() {
                                @Override
                                public void toSet() {
                                    /**
                                     * 去设置里设置权限
                                     */
                                    Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent1.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                                    startActivity(intent1);
                                }

                                @Override
                                public void cancle() {

                                }
                            }).show();
                        }
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:18667906808"));
                    startActivity(intent);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(CALL_PHONE_PERMISSION==requestCode){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:18667906808"));
                startActivity(intent);
            }else{
                Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
