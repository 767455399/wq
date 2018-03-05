package com.example.hasee.taiheapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.activity.call_phone.CallPhoneActivity;
import com.example.hasee.taiheapp.activity.menu.MenuActivity;
import com.example.hasee.taiheapp.activity.percent.PercentActivity;
import com.example.hasee.taiheapp.base.BaseActivity;

public class FunctionMenuActivity extends BaseActivity implements View.OnClickListener {
    private TextView handlerTextView, canvasTextView, permissionTextView, webViewTextView, callPhoneTextView, menuTextView, percentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_function_menu);
        handlerTextView=f(R.id.handlerTextView);
        canvasTextView = (TextView) findViewById(R.id.canvasTextView);
        permissionTextView = (TextView) findViewById(R.id.permissionTextView);
        webViewTextView = (TextView) findViewById(R.id.webViewTextView);
        callPhoneTextView = (TextView) findViewById(R.id.callPhoneTextView);
        menuTextView = (TextView) findViewById(R.id.menuTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        handlerTextView.setOnClickListener(this);
        canvasTextView.setOnClickListener(this);
        permissionTextView.setOnClickListener(this);
        webViewTextView.setOnClickListener(this);
        callPhoneTextView.setOnClickListener(this);
        menuTextView.setOnClickListener(this);
        percentTextView.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handlerTextView:
                startActivity(LoginActivity.class);
                break;
            case R.id.canvasTextView:
                startActivity(CanvasActivity.class);
                break;
            case R.id.permissionTextView:
                startActivity(PowerPermissionActivity.class);
                break;
            case R.id.webViewTextView:
                startActivity(WebViewActivity.class);
                break;
            case R.id.callPhoneTextView:
                startActivity(CallPhoneActivity.class);
                break;
            case R.id.menuTextView:
                startActivity(MenuActivity.class);
                break;
            case R.id.percentTextView:
                startActivity(PercentActivity.class);
                break;
            default:
                break;

        }
    }

//    public void startActivity(Class<?> clz) {
//        startActivity(new Intent(FunctionMenuActivity.this,clz));
//    }

}
