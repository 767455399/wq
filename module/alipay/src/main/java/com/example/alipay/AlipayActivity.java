package com.example.alipay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.alipay.util.PayResult;

import java.util.Map;

/**
 * 支付宝支付
 */
public class AlipayActivity extends AppCompatActivity {
    private static final int SDK_PAY_FLAG=1;
    private TextView aliPayTextView;
    private EditText aliPayInfoEditText;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayResult payResult=new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
         * 沙箱环境代码，如没有这行代码，会默认调用支付宝网页版，不加这行代码，则表示是正式环境
         * 不是沙箱环境必须注释下面这行代码。
         */
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        aliPayInfoEditText=(EditText)findViewById(R.id.aliPayInfoEditText);
        aliPayTextView =(TextView)findViewById(R.id.aliPayTextView);
        aliPayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            String orderInfo=aliPayInfoEditText.getText().toString();
            if(TextUtils.isEmpty(orderInfo)){
               Toast.makeText(AlipayActivity.this,"请输入支付订单信息",Toast.LENGTH_SHORT).show();
               return;
            }
//            final String orderInfo="alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016091300498733&biz_content=%7B%22body%22%3A%22%E6%B5%8B%E8%AF%95%E7%9F%BF%E6%B3%89%E6%B0%B40.02%E5%85%83%22%2C%22out_trade_no%22%3A%22CDD2018022611201976849520%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E7%9F%BF%E6%B3%89%E6%B0%B4%22%2C%22total_amount%22%3A%220.02%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F115.236.161.67%3A50076%2Ffunds-channel-notify%2Fnotify%2Fpay%2FALIPAY_QUICK_MSECURITY_PAY%2F2016091300498733%2Fgateway.notify&sign=FEyrG2qCAqoukk1OnM7DaRu93SrFjbFqQlwSSMMphx5LQI0lIwWy8QrwpcLEYnyJCYIIQYvzokHCbiyXrzLeKXLIcqaKe6GGkpxICFQNBzkXp1XDnbz7eQ59Miz2wX5cHW09jM3yney4FY%2Fl1qm36fXvcs4jKQk3Su%2BGR6pNI8WAl4iX%2BJJqYZrOUYUxKetzOkfIL%2B5FSlFSwkRe0Sa%2BRvMcbc4ZCK3t3xz%2BKY0dwSykQdrUAsTcSup6lw5RPY9kpXChkuMlWA5vIdMoP5RAH%2BKroc8Zfs8uszN5Xg0BqMAAD%2FHoWgw%2F5j5IMG5zwQ8JjgykKBzPm0TBOMkg779XjQ%3D%3D&sign_type=RSA2&timestamp=2018-02-26+11%3A20%3A31&version=1.0";
            PayTask alipay=new PayTask(AlipayActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Message message=new Message();
            message.what=SDK_PAY_FLAG;
            message.obj=result;
            handler.sendMessage(message);
        }
    };
}
