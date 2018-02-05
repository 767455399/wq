package com.example.hasee.wq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hasee.wq.R;
import com.example.hasee.wq.activity.startup_mode.SingleTopActivity;
import com.example.hasee.wq.api.AccountApi;
import com.example.hasee.wq.modle.AccountModle;
import com.example.hasee.wq.modle.LoginModle;
import com.example.hasee.wq.modle.SportsRuleModel;
import com.example.hasee.wq.server.LoginService;

import gorden.rxbus2.Subscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wangqing on 2017/11/4.
 */

public class LoginActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://passport.trc.com/account/";
    public static final String TRPAY_BASE_URL = "https://pay.trc.com/";
    public static final String SPORT_BASE_URL = "https://appapi.trc.com/";
    private EditText accountEditText;
    private EditText passwordEditText;
    private Button sureButton;
    private String account;
    private String password;
    private Retrofit retrofit;
    private OkHttpClient.Builder builder;
    private OkHttpClient okHttpClient;
    public static String sToken = "10F557064A3048E0A785E31433FBA32A.CE2F426542E19540767033D5BB70268C";
    private SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 初始化数据
     */
    public void init() {
        sp = getSharedPreferences("data", 0);
        accountEditText = (EditText) findViewById(R.id.accountEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        sureButton = (Button) findViewById(R.id.sureButton);
        okHttpClient = new OkHttpClient();
        accountEditText.setText("18667906808");
        passwordEditText.setText("wq123456");
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = accountEditText.getText().toString();
                password = passwordEditText.getText().toString();
                //保存数据；
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", account);
                editor.putString("psw", password);
                editor.commit();
                login(account, password);

            }
        });
    }


    public void login(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "请输入登陆账号", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请输入登陆密码", Toast.LENGTH_LONG).show();
        } else {
            String account1 = sp.getString("name", "zhangsan");
            String password1 = sp.getString("psw", "123");
            newRequestData(account, password);
//            newLoadSport();
        }

    }


    public void newRequestData(String account, String password) {
        AccountApi.getLogin(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModle>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginModle loginModle) {
                        Toast.makeText(LoginActivity.this, "登陆成功啦！", Toast.LENGTH_LONG).show();
//                        newLoadSport();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "失败啦！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 请求登陆接口
     */
//    public void requestData(String account, String password) {
//        builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.SECONDS);
//        builder.readTimeout(10, TimeUnit.SECONDS);
//        builder.writeTimeout(10, TimeUnit.SECONDS);
//        builder.cookieJar(new CookiesManager(new PersistentCookieStore(this)));
////        builder.cookieJar(new CookieJar() {
////            Map<String, List<Cookie>> cookieMap = new HashMap<>();
////
////            @Override
////            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
////                for (Cookie cookie : cookies) {
////                    if ("token".equals(cookie.name())) {
////                        sToken = cookie.value();
////                        break;
////                    }
////                }
////                cookieMap.put(url.host(), cookies);
////            }
////
////            @Override
////            public List<Cookie> loadForRequest(HttpUrl url) {
////                List<Cookie> list = cookieMap.get(url.host());
////                List<Cookie> cookieList = new ArrayList(1);
////                if (null != list) {
////                    cookieList.addAll(list);
////                }
////                if (!TextUtils.isEmpty(sToken)) {
////                    Cookie tokenCookie = null;
////                    for (Cookie cookie : cookieList) {
////                        if ("token".equals(cookie.name())) {
////                            tokenCookie = cookie;
////                            break;
////                        }
////                    }
////                    if (null == tokenCookie || !sToken.equals(tokenCookie.value())) {
////                        cookieList.remove(tokenCookie);
////                        tokenCookie = new Cookie.Builder().domain(url.host()).name("token").value(sToken).build();
////                        cookieList.add(tokenCookie);
////                    }
////                }
////                return cookieList;
////            }
////        });
//        okHttpClient=builder.build();
//        retrofit = new Retrofit.Builder()
//                .client(builder.build())
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        LoginService loginService = retrofit.create(LoginService.class);
//        Observable<LoginModle> observable = loginService.reqLogin(account, password, true);
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<LoginModle>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(LoginModle loginModle) {
//                        requestAccount();
//                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
////                        startActivity(new Intent(LoginActivity.this,CanvasActivity.class));
////                        loadSport();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//
//    }
    private void requestAccount() {
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(TRPAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(LoginService.class)
                .reqAccount("trc")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AccountModle>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccountModle accountModle) {
                        Toast.makeText(LoginActivity.this, "查询账户成功", Toast.LENGTH_LONG).show();
                        loadSport();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void newLoadSport() {
//        AccountApi.getSportRule().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<SportsRuleModel>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(SportsRuleModel sportsRuleModel) {
//                Toast.makeText(LoginActivity.this, "运动规则请求成功啦", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(LoginActivity.this, "运动规则请求失败啦", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        AccountApi.getSportRule().flatMap(new Function<SportsRuleModel, ObservableSource<AccountModle>>() {
//            @Override
//            public ObservableSource<AccountModle> apply(SportsRuleModel sportsRuleModel) throws Exception {
//                return AccountApi.getAccount();
//            }
//        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<AccountModle>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(AccountModle accountModle) {
//                Toast.makeText(LoginActivity.this, accountModle.setSecurityQuestion + "请求成功拉啦", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        AccountApi.getAccount().flatMap(new Function<AccountModle, ObservableSource<SportsRuleModel>>() {
            @Override
            public ObservableSource<SportsRuleModel> apply(AccountModle accountModle) throws Exception {
                return AccountApi.getSportRule();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<SportsRuleModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SportsRuleModel sportsRuleModel) {
                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,SingleTopActivity.class));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    @Subscribe(code = 001)
    public void refresh(String msg){
        sureButton.setText(msg);
//        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    private void loadSport() {
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(SPORT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(LoginService.class)
                .reqRule()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SportsRuleModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SportsRuleModel sportsRuleModel) {
                        Toast.makeText(LoginActivity.this, "请求成功啦", Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, sportsRuleModel.rule, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
