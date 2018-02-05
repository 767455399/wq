package com.example.hasee.wq.api;

import com.example.hasee.wq.http.RetrofitClient;
import com.example.hasee.wq.modle.AccountModle;
import com.example.hasee.wq.modle.LoginModle;
import com.example.hasee.wq.modle.SportsRuleModel;
import com.example.hasee.wq.server.LoginService;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by wangqing on 2017/12/18.
 */

public class AccountApi {
    public static final String BASE_URL_LOGIN="https://passport.trc.com/account/";
    public static final String BASE_URL_SPORT = "https://appapi.trc.com/";
    public static final String BASE_URL_TRPAY = "https://pay.trc.com/";

    public static Observable<LoginModle> getLogin(String account, String password){
        Retrofit retrofit= RetrofitClient.getRetrofitBuilder(BASE_URL_LOGIN).build();
        return retrofit.create(LoginService.class).reqLogin(account, password, true);
    }

    public static Observable<SportsRuleModel>getSportRule(){
        return RetrofitClient.getRetrofitBuilder(BASE_URL_SPORT).build()
                .create(LoginService.class).reqRule();
    }

    public static Observable<AccountModle>getAccount(){
        return RetrofitClient.getRetrofitBuilder(BASE_URL_TRPAY).build()
                .create(LoginService.class).reqAccount("trc");
    }

}
