package com.tairanchina.taiheapp.wxapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tairanchina.taiheapp.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if(0==baseResp.errCode){
                Toast.makeText(WXPayEntryActivity.this,"微信支付成功",Toast.LENGTH_SHORT).show();
            }else if(1==baseResp.errCode){
                Toast.makeText(WXPayEntryActivity.this,"微信支付错误",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(WXPayEntryActivity.this,"微信支付取消",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
