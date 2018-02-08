package com.example.hasee.wq.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hasee.wq.tools.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

    }

    protected abstract void initView();

    public abstract void initData();

    public void toast(String msg){
        ToastUtil.showNormalToast(this,msg);
    }
    
    
    
    
    
}
