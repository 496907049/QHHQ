package com.example.qhhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
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

public class WebViewActivity extends Activity {

    String url;
    private WebView mWebView;
    String title;
    String data;
    String text;
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

        handler = new Handler() {
            public void handleMessage(Message msg) {
                String notice1 = "<html>"
                        + "<head>"
                        + "<link rel=\"stylesheet\" href=\"http://img.kxt.com/Public/App/css/global.css?v=3.0.1.12\">"
                        + "<link href=\"http://img.kxt.com/Public/App/video/css/bofang.css?v=3.0.1.12\" rel=\"stylesheet\" type=\"text/css\">"
                        + "</head>"
                        + "<body>"
                        + "<div class =\"nei content snei\">"
                        + "<h1>" + title + "</h1>"
                        + "<div class =\"g_tag\">"
                        + data
                        + "</div>"
                        + "<div class =\"article\">"
                        + text
                        + "</div>"
                        + "</div>"
                        + "</body></html>";
                mWebView.loadDataWithBaseURL("about:blank", notice1, "text/html", "utf-8", null);
            }
        };
        Thread thread = new Thread(new loadDateThreah());
        thread.start();
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

    class loadDateThreah implements Runnable {
        @Override
        public void run() {

            try{
                Parser parser = HtmlParserUtils.getParserWithUrlConn2(url,"utf-8");
                // 这里是控制测试的部分，后面的例子修改的就是这个地方。
                NodeFilter filter = new TagNameFilter("div");
                NodeList nodes = parser.extractAllNodesThatMatch(filter);

                if(nodes!=null) {
                    final int size = nodes.size();
                    for (int i = 0; i < nodes.size(); i++) {
                        Node node = (Node) nodes.elementAt(i);
                        if("div class=\"nei content snei\"".equals(node.getText())){
                            NodeList titleList = node.getChildren();
                            title = titleList.elementAt(1).toHtml();
                        }

                        if("div id=\"article\"".equals(node.getText())){
                            NodeList textList = node.getChildren();
                            text=textList.toHtml();
                        }

                        if ("div class=\"g_tag\"".equals(node.getText())){
                            NodeList dataList = node.getChildren();
                            data = dataList.elementAt(2).toHtml();
                        }
                    }
                    handler.sendEmptyMessage(200);
                }
            }
            catch( Exception e ) {
                e.printStackTrace();
            }

        }




    }



}
