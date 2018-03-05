package com.example.hasee.taiheapp.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.example.hasee.taiheapp.tools.FastClick;
import com.example.hasee.taiheapp.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity {
    private boolean availableNetworks;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toast((String) msg.obj);
            handler.postDelayed(runnable, 10000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        initData();
        steepStatusBar();
        forcedVerticalScreen();
        /**
         * 开启定时执行任务；
         */
//        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            message.obj = "定时任务";
            handler.sendMessage(message);
        }
    };

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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            availableNetworks = true;
        } else {
            availableNetworks = false;
        }
        return availableNetworks;
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

    /**
     * fragment跳转不需要进栈
     */
    public void replaceFragment(Fragment fragment) {
        try {
//            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT,fragment).commit();
            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, fragment, fragment.getClass().getName()).commit();
        } catch (Throwable e) {
        }
    }

    /**
     * fragment跳转不需要进栈
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT,fragment,fragment.getClass().getName()).commit();
        }catch (Throwable e){

        }
    }

    /**
     * fragment跳转，需要进栈（replace方法）
     */
    public void replaceFragmentNeedToStack(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT,fragment,fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commit();
        }catch (Throwable e){

        }
    }

    /**
     * fragment跳转，需要进栈。
     */
    public void addFragmentNeedToStack(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT,fragment,fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commit();
        }catch (Throwable e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideSoftInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
