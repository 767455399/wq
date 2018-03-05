package com.example.hasee.taiheapp.activity.qr_code_scan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.hasee.taiheapp.tools.ToastUtil;
import com.example.mylibrary.CaptureActivity;
import com.google.zxing.Result;

public class QRCodeScanActivity extends CaptureActivity {
    public static final String RESULT_KEY = "result_key";

    public static Intent newIntent(Context context) {
        Intent it = new Intent(context, QRCodeScanActivity.class);
        return it;
    }

    @Override
    protected void handleResult(Result result) {
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString)) {
            restartPreview();
        } else {
            ToastUtil.showNormalToast(resultString);
            char[] chars = resultString.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] -= chars[i] > 128 ? 128 : 0;
            }
            resultString = new String(chars);
            Intent intent = new Intent();
            intent.putExtra(RESULT_KEY, resultString);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
