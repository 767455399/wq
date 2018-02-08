package com.example.hasee.wq.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by wangqing on 2018/2/8.
 */

public class ToastUtil {
    public static Context mContext;
    private static Toast toast=null;

    public static void showNormalToast(Context context, final String message){
        mContext=context;
        if(TextUtils.isEmpty(message))
            return;
        if(Looper.myLooper()==Looper.getMainLooper()){
            toast(message);
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    toast(message);
                }
            });
        }
    }
    private static void toast(String message) {
        if(toast!=null){
            toast.cancel();
        }
        if(TextUtils.isEmpty(message))
            return;
       toast=Toast.makeText(mContext,message,Toast.LENGTH_SHORT);
       toast.show();
    }
}
