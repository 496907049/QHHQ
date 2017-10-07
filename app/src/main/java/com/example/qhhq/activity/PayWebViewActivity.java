package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Util.PreferencesUtils;
import com.example.qhhq.R;

/**
 * Created by asus01 on 2017/9/25.
 */

public class PayWebViewActivity extends Activity {

//    String url = "http://wxpay.wxutil.com/mch/pay/h5.v2.php";
    String url = "  http://www.cgkjs.com/";

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_pay_web_view);

        mWebView = (WebView) findViewById(R.id.ww_pay);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                if (TextUtils.isEmpty(url))
                    return false;
                WebView.HitTestResult hitTestResult = view.getHitTestResult();

                if (hitTestResult != null && !url.startsWith("https://wx")) {
                    if (parseScheme(url)) {
                        try {
                            Log.e("url:", "url:" + url);
                            Intent intent;
                            intent = Intent.parseUri(url,
                                    Intent.URI_INTENT_SCHEME);
                            intent.addCategory("android.intent.category.BROWSABLE");
                            intent.setComponent(null);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        view.loadUrl(url);
                    }
                    return true;
                } else {
                    if (parseScheme(url)) {
                        try {
                            Log.e("url:", "url:" + url);
                            Intent intent;
                            intent = Intent.parseUri(url,
                                    Intent.URI_INTENT_SCHEME);
                            intent.addCategory("android.intent.category.BROWSABLE");
                            intent.setComponent(null);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                        view.loadUrl(url);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理  如：
                view.loadDataWithBaseURL(null, "<span style=\"color:#FFFFFF\"></span>", "text/html", "utf-8", null);
                //				handler.postDelayed(task, 200);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受信任所有网站的https证书
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //                view.loadUrl("javascript:window.android.getSource('<head>'+" +
                //                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                if (url.startsWith("http://www.baidu.com")) {
//                    CookieManager cookieManager = CookieManager.getInstance();
//                    String cookieStr = cookieManager.getCookie(url);
//                    cookieManager.setCookie(link, cookieStr);
//                    PreferencesUtils.putString(PayWebViewActivity.this, Constant.URL_COOKIE, cookieStr);
//                    					syncCookie();
//                }
//                if (!webSettings.getLoadsImagesAutomatically()) {
//                    webSettings.setLoadsImagesAutomatically(true);
////                }
            }
        });

    }

    public boolean parseScheme(String url) {
        if (url.startsWith("alipay") | url.startsWith("weixin")) {
            return true;
        }
        if (url.startsWith("mqqapi://forward/url")) {
            return true;
        }
        if (url.contains("platformapi/startapp")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > 23)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        if(mWebView!=null){
            mWebView.destroy();
        }
        super.onDestroy();
    }


}
