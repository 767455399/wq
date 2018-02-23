package com.example.hasee.wq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hasee.wq.tools.ToastUtil;

/**
 * Created by wangqing on 2018/2/12.
 */

public class BroadCastReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.showNormalToast(intent.getStringExtra("wq"));
    }
}
