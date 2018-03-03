package com.example.hasee.wq.activity.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hasee.wq.R;
import com.example.hasee.wq.modle.LianLianPayModle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {
    private TextView receiveTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus2);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        receiveTextView = (TextView) findViewById(R.id.receiveTextView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventBusActivity.this,SendActivity.class));
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receive(LianLianPayModle lianLianPayModle){
        receiveTextView.setText("接收到广播"+lianLianPayModle.busi_partner+lianLianPayModle.no_order);
//        ToastUtil.showNormalToast(lianLianPayModle.busi_partner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
