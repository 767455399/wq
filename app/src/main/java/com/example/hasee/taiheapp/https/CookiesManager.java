/*
package com.example.hasee.wq.https;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

*/
/**
 * Created by wangqing on 2017/12/14.
 *//*


public class CookiesManager  implements CookieJar {
    private PersistentCookieStore persistentCookieStore;
//    public PersistentCookieStore cookieStore;

      public CookiesManager(PersistentCookieStore persistentCookieStore){
        this.persistentCookieStore=persistentCookieStore;
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                persistentCookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = persistentCookieStore.get(url);
        return cookies;
    }
}

*/
