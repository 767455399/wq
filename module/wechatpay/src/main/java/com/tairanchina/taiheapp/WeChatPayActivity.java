package com.tairanchina.taiheapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatPayActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private EditText weChatMesEditText;
    private Button weChatPayButton;
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_pay);
        iwxapi= WXAPIFactory.createWXAPI(WeChatPayActivity.this,"wxd9ef3b0afe696e5f");
        iwxapi.registerApp("wxd9ef3b0afe696e5f");
        initView();


    }

    private void initView() {
        weChatMesEditText=(EditText)findViewById(R.id.weChatMesEditText);
        weChatPayButton=(Button)findViewById(R.id.weChatPayButton);
        weChatPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=weChatMesEditText.getText().toString();
                if(!TextUtils.isEmpty(content)){
                    Toast.makeText(WeChatPayActivity.this,"请输入微信支付订单信息",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
//                          {"package":"Sign=WXPay","appid":"wxd9ef3b0afe696e5f","sign":"E0FA97A4A10EF4827FFF60B3F739BEAF",
// "partnerid":"1379524802","prepayid":"wx2018022810275516a2a2ae040840349434",
// "noncestr":"jXtyDLxvp8usQIN5","timestamp":"1519784887"}
                                PayReq req=new PayReq();
                                req.nonceStr="jXtyDLxvp8usQIN5";
                                req.appId="wxd9ef3b0afe696e5f";
                                req.sign="E0FA97A4A10EF4827FFF60B3F739BEAF";
                                req.prepayId="wx2018022810275516a2a2ae040840349434";
                                req.partnerId="1379524802";
//                                req.extData="app data";
                                req.timeStamp="1519784887";
                                req.packageValue="Sign=WXPay";
                                iwxapi= WXAPIFactory.createWXAPI(WeChatPayActivity.this,"wxd9ef3b0afe696e5f",true);
                                iwxapi.sendReq(req);
                        }
                    };
                    new Thread(runnable).start();

                }


            }
        });
    }

    @Override
    public void onReq(BaseReq baseReq) {


    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if(0==baseResp.errCode){
                Toast.makeText(WeChatPayActivity.this,"微信支付成功",Toast.LENGTH_SHORT).show();
            }else if(1==baseResp.errCode){
                Toast.makeText(WeChatPayActivity.this,"微信支付错误",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(WeChatPayActivity.this,"微信支付取消",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
