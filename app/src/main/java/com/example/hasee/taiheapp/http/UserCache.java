package com.example.hasee.taiheapp.http;

/**
 * Created by wangqing on 2017/12/15.
 */

public class UserCache {



    private static String tokenMemoryCache = "";
    private static String userId = "";
    private static String phone = "";


//    public static String getUserId() {
//        if (TextUtils.isEmpty(userId)) {
////            return AppPreferences.getInstance().getString("userId", "");
//        }
//        return userId;
//    }
//
//    public static void setUserId(String userId) {
//        UserCache.userId = userId;
////        AppPreferences.getInstance().put("userId", userId);
//    }
//
//    public static String getPhone() {
//        if (TextUtils.isEmpty(phone)) {
////            return AppPreferences.getInstance().getString("accountPhone", "");
//        }
//        return phone;
//    }
//
//    public static void setPhone(String phone) {
//        UserCache.phone = phone;
////        AppPreferences.getInstance().put("accountPhone", phone);
//    }
//
//    public static String getUserAgent() {
//        return AppPreferences.getInstance().getString("UserAgent", "");
//    }
//
//    public static void setUserAgent(String userAgent) {
//        AppPreferences.getInstance().put("UserAgent", userAgent);
//    }
//
//    public static Boolean getIsLogin() {
//        if (TextUtils.isEmpty(getToken())) {
//            return false;
//        } else {
//            return AppPreferences.getInstance().getBoolean("loginStatus", false);
//        }
//    }
//
//    public static void setIsLogin(Boolean isLogin) {
//        AppPreferences.getInstance().put("loginStatus", isLogin);
//    }
//
//
//    public static void saveToken(String token) {
//        tokenMemoryCache = token;
//        AppPreferences.getInstance().put("tokenCache", token);
//    }
//
//    public static String getToken() {
//        return TextUtils.isEmpty(tokenMemoryCache) ? AppPreferences.getInstance().getString("tokenCache", "") : tokenMemoryCache;
//    }
//
//
//    public static void clearToken() {
//        tokenMemoryCache = "";
//        AppPreferences.getInstance().put("tokenCache", "");
//        HttpClient.INSTANCE.clearCookie();
//
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.setCookie(BaseBuildConfig.getWebDomain(), "token=");
//    }
}
