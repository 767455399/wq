package com.example.hasee.wq.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangqing on 2017/12/15.
 */

public class AppVersionInterceptor  implements Interceptor {

    private static final String APP_VERSION_HEADER_NAME = "X-API-Version";
    private String prefix = "elife-android-";
    private final String appVersionHeaderValue;

    public AppVersionInterceptor(String appVersionHeaderValue) {
        this.appVersionHeaderValue = appVersionHeaderValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(APP_VERSION_HEADER_NAME)
                .addHeader(APP_VERSION_HEADER_NAME, prefix + appVersionHeaderValue)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}