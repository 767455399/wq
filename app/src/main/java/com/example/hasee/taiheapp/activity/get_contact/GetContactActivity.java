package com.example.hasee.taiheapp.activity.get_contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.dialog.PermissionDialog;
import com.example.hasee.taiheapp.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class GetContactActivity extends BaseActivity {
    public static final int READ_CONTACT_PERMISSION = 1;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private TextView getContactTextView;
    private TextView contentTextView;
    private TextView showTextView;
    private List<String>phoneList=new ArrayList<>();
    private StringBuffer stringBuffer=new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_get_contact);
        getContactTextView=f(R.id.getContactTextView);
        contentTextView=f(R.id.contentTextView);
        showTextView=f(R.id.showTextView);
        showTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuffer=null;
                for (int i = 0; i <phoneList.size(); i++) {
                    stringBuffer.append(phoneList.get(i));
                }
                contentTextView.setText(stringBuffer);
            }
        });
        getContactTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });


    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(GetContactActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(GetContactActivity.this,Manifest.permission.READ_CONTACTS)){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READ_CONTACT_PERMISSION);
            }else{
                if(this.hasWindowFocus()){
                    String content="请开启读取联系人权限";
                    new PermissionDialog(this, content, new PermissionDialog.Listener() {
                        @Override
                        public void toSet() {
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
        }else{
            getContentProvider();
        }
    }

    private void getContentProvider() {
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(null!=cursor){
            while (cursor.moveToNext()){
                //获取联系人姓名
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(TextUtils.isEmpty(name)){
                    ToastUtil.showNormalToast("未获取到联系人姓名");
                }else if(TextUtils.isEmpty(number)){
                    ToastUtil.showNormalToast("未获取到联系人号码");
                }else{
                }
                phoneList.add(name+":"+number);
            }
        }
    }

    @Override
    public void initData() {

    }
}
