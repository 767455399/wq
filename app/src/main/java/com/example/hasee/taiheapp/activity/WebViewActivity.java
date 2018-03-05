package com.example.hasee.taiheapp.activity;

import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.taiheapp.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private ImageView backImageView;
    private TextView titleTextView;
    private TextView refreshTextView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();

    }

    private void initView() {
        backImageView = (ImageView) findViewById(R.id.backImageView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        refreshTextView = (TextView) findViewById(R.id.refreshTextView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });
        refreshTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小；
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小；
        //缩放操作
        webSettings.setSupportZoom(true);//支持缩放，默认为true;
        webSettings.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该webview不可缩放。
        webSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件。
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过js打开新窗口；
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片；
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式；
        webSettings.setDefaultTextEncodingName("utf-8");
        //关于缓存的设置，没有网络的时候使用缓存，有网络的时候不适用缓存；
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://10.200.4.68:82/#/index");

        Toast.makeText(WebViewActivity.this,"Title"+webView.getTitle(),Toast.LENGTH_SHORT).show();
        titleTextView.setText(webView.getTitle());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                xtwq://paySDK_back_ecardList?age=1111
                Uri uri=Uri.parse(url);
                String path=uri.getPath();
                String scheme=uri.getScheme();
                String host=uri.getHost();
                String queryString=uri.getQuery();
                String queryParameter=uri.getQueryParameter("age");
//                String param=uri.getQueryParameter("age");

                xtwq://paySDK_back_ecardList
                if(scheme.equals("xtwq")){
                    Toast.makeText(WebViewActivity.this,"获取jsbridge成功了",Toast.LENGTH_SHORT).show();
                }
                switch (host){
                    case "paySDK_back_ecardList":
                        webView.loadUrl("javascript:xtwqCallBack('" + "fyd白霸天" + "')");
                        Toast.makeText(WebViewActivity.this,"获取host成功",Toast.LENGTH_SHORT).show();
                        break;
                    case "xyk://login":
                        Toast.makeText(WebViewActivity.this,"调取成功2",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:xtwqCallBack('" + "fyd白霸天" + "')");
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        /**
         * 加载本地html
         */
//        webView.loadUrl("file:///android_asset/xtwq.html");
//        WebSettings webSettings=webView.getSettings();
//        webView.loadUrl("http://10.200.4.68:82/#/index");
//        webView.loadUrl("https://www.baidu.com/?tn=80035161_1_dg");
//        webView.loadUrl("https://pay.trc.com/pocketWallet_h5/#/loading");

    }


    /**
     * 销毁webview
     * 在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
     * 但是注意：webview调用destory时,webview仍绑定在Activity上
     * 这是由于自定义webview构建时传入了该Activity的context对象
     * 因此需要先从父容器中移除webview,然后再销毁webview:
     */
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    /**
     * 物理返回按钮返回操作
     * 问题：在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身
     * 目标：点击返回后，是网页回退而不是推出浏览器
     * 解决方案：在当前Activity中处理并消费掉该 Back 事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
