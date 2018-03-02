package com.example.lianlianpay;

/**
 * Created by wangqing on 2018/3/2.
 */

public interface Callback<T> {
    void onSuccess(T t);

    void onFail(int errorCode, String errorMsg);
}
