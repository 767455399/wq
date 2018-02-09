package com.example.hasee.wq.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

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
    public boolean isRepeatedClicks() {
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
    public void forcedVerticalScreen() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 检测网络环境
     */
    public boolean isAvailableNetworks() {
        return false;
    }

    /**
     * 界面跳转
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 绑定view
     */
    protected <T extends View> T f(int id) {
        return (T) findViewById(id);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideSoftInput();
    }
}
