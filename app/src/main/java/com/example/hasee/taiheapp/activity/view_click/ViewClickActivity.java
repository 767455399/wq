package com.example.hasee.taiheapp.activity.view_click;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.tools.ToastUtil;

public class ViewClickActivity extends AppCompatActivity {
    private static final String TAG = "ViewClickActivity";
    private Button button;
    private LinearLayout linearLayout;
    private MotionEvent event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_click);
        initView();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showNormalToast("点击了按钮");
                Log.v(TAG, "button点击了按钮");
            }
        });

        /**
         * 对于View（注意！ViewGroup也是View）而言，如果设置了onTouchListener，那么OnTouchListener方法中的onTouch方法会被回调。
         * onTouch方法返回true，则onTouchEvent方法不会被调用（onClick事件是在onTouchEvent中调用）
         * 所以三者优先级是onTouch->onTouchEvent->onClick
         */
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG, "手指按下了");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "手指正在滑动");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "手指抬起了");
                        break;
                }
                return true;
            }
        });


        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "linearLayout手指点击");
                return true;
            }
        });



    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getWindow().superDispatchTouchEvent(ev)) {
            return false;
        }
        return onTouchEvent(ev);
    }
}
