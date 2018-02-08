package com.example.hasee.wq.activity.percent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.hasee.wq.R;
import com.example.hasee.wq.base.BaseActivity;

public class PercentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
//        ActionBar actionBar=getSupportActionBar();
//        if(actionBar!=null){
//            actionBar.hide();
//        }
        if(Build.VERSION.SDK_INT>=21){
            Window window=getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {

    }
    /**
     * 参考资料
     * http://blog.csdn.net/lmj623565791/article/details/46767825
     * app:layout_heightPercent="50%w"
     * app:layout_marginPercent="15%w",
     *app:layout_marginBottomPercent="20%h"
     * app:layout_marginPercent="15%w"
     *  app:layout_heightPercent="15%w"
     app:layout_marginPercent="5%w"
     app:layout_widthPercent="15%w"
     app:layout_heightPercent="15%"
     app:layout_marginPercent="5%"
     app:layout_widthPercent="15%"

     *
     */
}
