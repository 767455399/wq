package com.example.hasee.wq.activity.percent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.wq.R;
import com.example.hasee.wq.base.BaseActivity;

public class PercentActivity extends BaseActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /* 隐藏actionbar方法
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }*/



    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_percent);
    }

    @Override
    public void initData() {
        textView=(TextView)findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRepeatedClicks()){
                    toast("请勿快速点击");
                }else{
                    toast("响应点击事件");
                }
            }
        });


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
