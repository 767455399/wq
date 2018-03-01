package com.example.lianlianpay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LianLianPayActivity extends AppCompatActivity {
    private static final int REQ_PAY = 1;
    public static final String RET_CODE_SUCCESS = "0000";// 0000 交易成功
    public static final String RET_CODE_PROCESS = "2008";// 2008 支付处理中
    public static final String RESULT_PAY_SUCCESS = "SUCCESS";
    public static final String RESULT_PAY_PROCESSING = "PROCESSING";
    public static final String RESULT_PAY_FAILURE = "FAILURE";
    public static final String RESULT_PAY_REFUND = "REFUND";
    private Button lianLianPayButton;
    // 支付验证方式 0：标准版本， 1：卡前置方式，接入时，只需要配置一种即可，Demo为说明用。可以在menu中选择支付方式。
    private int pay_type_flag = 0;
    private boolean is_preauth = false;
    PayOrder payOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lian_lian_pay);
        lianLianPayButton = (Button) findViewById(R.id.lianLianPayButton);
        lianLianPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lianLianPay();
            }
        });

    }
//
//    "oid_partner":"201408071000001543",
//            "risk_item":"{\"user_info_bind_phone\":\"18602702340\",\"user_info_mercht_userno\":\"testUserId\",\"frms_ware_category\":\"1999\",\"user_info_dt_register\":\"20180228104508\"}",
//            "sign":"837b950bba9bf484edfcd6cd8e66a349",
//            "dt_order":"20180228104508",
//            "name_goods":"测试矿泉水",
//            "notify_url":"http://115.236.161.67:50076/funds-channel-notify/notify/pay/LIANLIAN_APP/201408071000001543/gateway.notify",
//            "version":"1.0",
//            "busi_partner":"101001",
//            "no_order":"CDD2018022810450839952877",
//            "user_id":"testUserId",
//            "money_order":0.01,
//            "sign_type":"MD5",
//            "valid_order":"10080"
//}

    private void lianLianPay() {
        String sign = null;
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        PayOrder payOrder = new PayOrder();
        payOrder.setNo_order(timeString);
        payOrder.setDt_order(timeString);
        payOrder.setOid_partner("201408071000001543");//000
        payOrder.setRisk_item(constructRiskItem());//000
//        payOrder.setDt_order("20180228104508");//000
        payOrder.setName_goods("测试矿泉水");//000
        payOrder.setNotify_url("http://115.236.161.67:50076/funds-channel-notify/notify/pay/LIANLIAN_APP/201408071000001543/gateway.notify");//000
        payOrder.setBusi_partner("101001");//000
//        payOrder.setNo_order("CDD2018022810450839952877");//000
        payOrder.setUser_id("testUserId");
        payOrder.setMoney_order("0.01");
        payOrder.setSign_type("MD5");//000
        payOrder.setValid_order("10080");//000
        payOrder.setFlag_modify("1");
        try {
            sign = URLEncoder.encode("837b950bba9bf484edfcd6cd8e66a349", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        payOrder.setSign(sign);//000
        String content4Pay = BaseHelper.toJSONString(payOrder);
        MobileSecurePayer mobileSecurePayer = new MobileSecurePayer();
        mobileSecurePayer.pay(content4Pay, mHandler, REQ_PAY, LianLianPayActivity.this, false);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String strRet = (String) msg.obj;
            switch (msg.what) {
                case 1: {
                    JSONObject objContent = BaseHelper.string2JSON(strRet);
                    String retCode = objContent.optString("ret_code");
                    String retMsg = objContent.optString("ret_msg");

                    // 成功
                    if (RET_CODE_SUCCESS.equals(retCode)) {
                        // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉
                        if (pay_type_flag == 1) {
                            TextView tv_agree_no = (TextView) findViewById(R.id.tv_agree_no);
                            tv_agree_no.setVisibility(View.VISIBLE);
                            tv_agree_no.setText(objContent.optString(
                                    "agreementno", ""));
                        }
                        BaseHelper.showDialog(LianLianPayActivity.this, "提示",
                                "支付成功，交易状态码：" + retCode + " 返回报文:" + strRet,
                                android.R.drawable.ic_dialog_alert);
                    } else if (RET_CODE_PROCESS.equals(retCode)) {
                        // TODO 处理中，掉单的情形
                        String resulPay = objContent.optString("result_pay");
                        if (RESULT_PAY_PROCESSING
                                .equalsIgnoreCase(resulPay)) {
                            BaseHelper.showDialog(LianLianPayActivity.this, "提示",
                                    objContent.optString("ret_msg") + "交易状态码："
                                            + retCode + " 返回报文:" + strRet,
                                    android.R.drawable.ic_dialog_alert);
                        }

                    } else {
                        // TODO 失败
                        BaseHelper.showDialog(LianLianPayActivity.this, "提示", retMsg
                                        + "，交易状态码:" + retCode + " 返回报文:" + strRet,
                                android.R.drawable.ic_dialog_alert);
                    }
                }
                break;
            }
            super.handleMessage(msg);

        }
    };

    private String constructRiskItem() {
        JSONObject mRiskItem = new JSONObject();
        try {
            mRiskItem.put("user_info_bind_phone", "18602702340");
            mRiskItem.put("user_info_dt_register", "20180228104508");
            mRiskItem.put("frms_ware_category", "1999");
            mRiskItem.put("user_info_mercht_userno", "testUserId");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mRiskItem.toString();
    }
}
