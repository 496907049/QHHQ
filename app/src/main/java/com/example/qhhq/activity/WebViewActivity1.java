package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Util.HtmlParserUtils;
import com.example.qhhq.R;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

/**
 * Created by asus01 on 2017/9/25.
 */

public class WebViewActivity1 extends Activity {

    String url;
    private WebView mWebView;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从Intent当中根据key取得value
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        }
       setContentView(R.layout.activity_web_view);

        TextView textView = (TextView) findViewById(R.id.tv_main_title);
        textView.setText("详情");
        ImageView imageView = (ImageView) findViewById(R.id.iv_main_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.wv_act);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new myWebViewClient());

        mWebView.loadUrl(url);

    }

    @Override
    protected void onDestroy() {
        if(mWebView!=null){
            mWebView.destroy();
        }
        super.onDestroy();
    }
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


}
