package com.example.hasee.taiheapp.server;

import com.example.hasee.taiheapp.modle.AccountModle;
import com.example.hasee.taiheapp.modle.LianLianPayModle;
import com.example.hasee.taiheapp.modle.LoginModle;
import com.example.hasee.taiheapp.modle.SportsRuleModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangqing on 2017/12/6.
 */

public interface LoginService {


    /**
     * 登陆接口
     *
     * @param phone
     * @param password
     * @param rememberMe
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginModle> reqLogin(@Field("phone") String phone, @Field("password") String password, @Field("rememberMe") boolean rememberMe);

    /**
     * 账号状态查询
     *
     * @param from
     * @return
     */
    @GET("funds/account/check/base/info?from=")
    Observable<AccountModle> reqAccount(@Query("from") String from);


    /**
     * 运动规则
     *
     * @return
     */
    @GET("app/run/rule")
    Observable<SportsRuleModel> reqRule();

    /**
     * 连连支付
     */
    @GET("lianlian")
    Observable<LianLianPayModle>pay();




}
