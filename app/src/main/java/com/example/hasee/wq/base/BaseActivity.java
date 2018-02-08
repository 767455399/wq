package com.example.hasee.wq.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.example.hasee.wq.tools.FastClick;
import com.example.hasee.wq.tools.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        steepStatusBar();
        forcedVerticalScreen();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

    }

    protected abstract void initView();

    public abstract void initData();

    /**
     * 显示toast
     *
     * @param msg
     */
    public void toast(String msg) {
        ToastUtil.showNormalToast(msg);
    }

    /**
     * 防止按钮被快速点击
     */
    public boolean isfastClick() {
        return FastClick.isFastClick();
    }

    /**
     * 是否设置沉浸式布局
     */
    public void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 强制竖屏
     */
    public void forcedVerticalScreen(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


}
