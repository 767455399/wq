package com.example.hasee.taiheapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.dialog.PermissionDialog;

/**
 * 权限申请
 */

public class PowerPermissionActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO_WITH_DATA = 0;
    public static final int CAMERA_PERMISSION = 1;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_permission);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionApplication();
//                takePhote();
            }
        });
    }

    private void takePhote() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_WITH_DATA);
    }

    private void permissionApplication() {
        /**
         * 检验权限，如果权限未被允许，则重新申请；
         */
        if (ContextCompat.checkSelfPermission(PowerPermissionActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            /**
             * 如果权限未被禁止弹框则继续申请，并告知用途以及不允许权限的弊端。
             */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(PowerPermissionActivity.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(PowerPermissionActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            } else {
                /**
                 * 完全禁止且无法再弹权限申请框，这时弹框提示用户去设置开启权限。
                 */
                if(this.hasWindowFocus()){
//                    String content = "请在设置-应用-小测试-权限中开启电话权限，以正常使用网络访问、拨打客服热线等功能";
                    String content="权限中开启拍照权限";
                    new PermissionDialog(this, content, new PermissionDialog.Listener() {
                        @Override
                        public void toSet() {
                            /**
                             * 去设置里设置权限
                             */
//                            Uri packageURI = Uri.parse(PACKAGE_URL_SCHEME+"com.example.hasee.wq");
//                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                            startActivity(intent);

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
            takePhote();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhote();
            } else {
                Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
