package com.tairanchina.taiheapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangqing on 2018/2/26.
 */

public class WeChatPayMesModle {

    /**
     * package : Sign=WXPay
     * appid : wxd9ef3b0afe696e5f
     * sign : 5F03EAD1ADA359D16A7207BBDEF6A036
     * partnerid : 1379524802
     * prepayid : wx20180305112137bf2ea553950123112682
     * noncestr : Ejf5rOgCiLumiX6y
     * timestamp : 1520218819
     */

    @SerializedName("package")
    public String packageX;
    public String appid;
    public String sign;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
}
