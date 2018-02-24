package com.example.mylibrary.decoding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.example.mylibrary.camera.ViewfinderView;
import com.google.zxing.Result;

/**
 * Created by wangqing on 2018/2/24.
 */

public interface CaptureActivityInterface {
    Handler getHandler();

    void handleDecode(Result obj, Bitmap barcode);

    void setResult(int resultOk, Intent obj);

    void finish();

    void startActivity(Intent intent);

    void drawViewfinder();

    ViewfinderView getViewfinderView();
}
