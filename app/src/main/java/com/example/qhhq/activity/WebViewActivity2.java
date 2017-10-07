package com.example.qhhq.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.example.Util.Contants;
import com.example.Util.FileUtils;
import com.example.Util.HttpClientUtil;
import com.example.Util.ToastUtils;
import com.example.qhhq.R;
import com.example.qhhq.bean.FirstInterface;
import com.example.qhhq.bean.JPushMessage;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.widagt.DownPicUtil;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by asus01 on 2017/9/25.
 */

public class WebViewActivity2 extends Activity implements View.OnClickListener{

    String url;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private WebView mWebView;
    RelativeLayout webHomeLL;
    RelativeLayout leftArrowLL;
    RelativeLayout rightArrowLL;
    RelativeLayout refreshArrowLL;
    RelativeLayout menuArrowLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从Intent当中根据key取得value
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        }
       setContentView(R.layout.activity_web_view2);

        webHomeLL = (RelativeLayout) findViewById(R.id.web_home);
        leftArrowLL = (RelativeLayout) findViewById(R.id.web_left_arrow);
        rightArrowLL = (RelativeLayout) findViewById(R.id.web_right_arrow);
        refreshArrowLL = (RelativeLayout) findViewById(R.id.web_refresh);
        menuArrowLL = (RelativeLayout) findViewById(R.id.web_menu);
        webHomeLL.setOnClickListener(this);
        leftArrowLL.setOnClickListener(this);
        rightArrowLL.setOnClickListener(this);
        refreshArrowLL.setOnClickListener(this);
        menuArrowLL.setOnClickListener(this);
        mWebView = (WebView) findViewById(R.id.wv_act2);

        initWebView();

    }


    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length-1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
            Toast.makeText(WebViewActivity2.this,"图片保存图库成功",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下返回键并且webview界面可以返回
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
//        else{
//            finish();
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if(mWebView!=null){
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.web_home:
                mWebView.loadUrl(url);
                break;
            case R.id.web_left_arrow:
                mWebView.goBack(); // goBack()表示返回WebView的上一页面
                break;
            case R.id.web_right_arrow:
                mWebView.goForward();
                break;
            case R.id.web_refresh:
                mWebView.reload();
                break;
            case R.id.web_menu:
                showPopupWindow(view);
                break;
            case R.id.pop_share:
                getOpen();
                break;
            case R.id.pop_message:
                showMessage();
                break;
            case R.id.pop_clear:
            clearWebViewCache();
                ToastUtils.show(this,"清除完毕");
                break;
        }
    }

    private void showMessage() {
            String message =  JPushMessage.getContent();
        if( message == null){
            message = "没有信息";
        }

        new AlertDialog.Builder(WebViewActivity2.this).setTitle("信息")//设置对话框标题
                .setMessage(""+ message)//设置显示的内容
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                    }

                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮

            @Override

            public void onClick(DialogInterface dialog, int which) {//响应事件

            }

        }).show();//在按键响应事件中显示此对话框

    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {//5.0以上的手机https页面打不开图片
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebChromeClient(new WebChromeClient(){
//            //选择图片
//            public boolean onShowFileChooser(
//                    WebView webView, ValueCallback<Uri[]> filePathCallback,
//                    WebChromeClient.FileChooserParams fileChooserParams) {
//                if (mFilePathCallback != null)
//                    return true;
//                mFilePathCallback = filePathCallback;
//                selectImage();
//                return true;
//            }
//            // For Android 3.0+
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                if (mUploadMessage != null) return;
//                mUploadMessage = uploadMsg;
//                selectImage();
//            }
//            // For Android < 3.0
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooser( uploadMsg, "" );
//            }
//            // For Android  > 4.1.1
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                openFileChooser(uploadMsg, acceptType);
//            }

            //重写标题
            @Override
            public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new AlertDialog.Builder(WebViewActivity2.this)
                                .setTitle("提示")
                                .setMessage(""+message)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();

                    }
                });
                result.confirm();//这里必须调用，否则页面会阻塞造成假死
                return true;
            }
        });
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
// 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        //如果不设置WebViewClient，请求会跳转系统浏览器  
        mWebView.setWebViewClient(new myWebViewClient());
//显示页面

        // 长按点击事件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if(hitTestResult.getType()== WebView.HitTestResult.IMAGE_TYPE||
                        hitTestResult.getType()== WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
                    // 弹出保存图片的对话框

                    List<TieBean> strings = new ArrayList<TieBean>();
                    strings.add(new TieBean("保存图片到本地"));
                    DialogUIUtils.showSheet(WebViewActivity2.this, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                        @Override
                        public void onItemClick(CharSequence text, int position) {
                            String url = hitTestResult.getExtra();
                            // 下载图片到本地
                            DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                                @Override
                                public void getDownPath(String s) {
                                    Toast.makeText(WebViewActivity2.this,"下载完成",Toast.LENGTH_LONG).show();
                                    Message msg = Message.obtain();
                                    msg.obj=s;
                                    handler.sendMessage(msg);
                                }
                            });
                        }

                        @Override
                        public void onBottomBtnClick() {
                        }
                    }).show();
                }
                return true;
            }
        });

        mWebView.loadUrl(url);
    }


    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

        /**
         * 清除WebView缓存
         */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
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

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }


    private void showPopupWindow(View v) {
        View view = View.inflate(this, R.layout.pop_up_window, null);
        TextView share = (TextView)view.findViewById(R.id.pop_share);
        TextView message = (TextView)view.findViewById(R.id.pop_message);
        TextView clear = (TextView)view.findViewById(R.id.pop_clear);
        share.setOnClickListener(this);
        message.setOnClickListener(this);
        clear.setOnClickListener(this);
        //获取PopupWindow中View的宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        PopupWindow popupWindow = new PopupWindow(view, measuredWidth,measuredHeight);
        popupWindow.setFocusable(true);//popupwindow设置焦点

        popupWindow.setBackgroundDrawable(new ColorDrawable(0xaa000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        // popupWindow.showAsDropDown(v,0,0);
        //获取点击View的坐标
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //显示在正上方
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - measuredWidth / 2, location[1]-measuredHeight-10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);//设置动画
    }

    public void getOpen(){

        String url = Contants.SHARE_HTTP_BASE7;

        HttpClientUtil.getInstance(this).get(url,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                try {
                    Gson gson = new Gson();
                    JSONObject Info = new JSONObject(string);
                    JSONObject root = new JSONObject(Info.getString("msg"));
                    String bb = root.toString();
                    FirstInterface bean  = gson.fromJson(bb,FirstInterface.class);
                   showOpen(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }

    public void showOpen(FirstInterface bean){

        if("0".equals(bean.getOpen())){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "");
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "分享"));
        }else{
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, ""+bean.getLinks());
            shareIntent.putExtra(Intent.EXTRA_TITLE, "分享测试开关.");
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "分享"));
        }
        //                startActivity(shareIntent);
    }
}
