package com.example.Util;

import android.content.Context;

import com.example.qhhq.http.AsyncHttpClient;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.http.BinaryHttpResponseHandler;
import com.example.qhhq.http.JsonHttpResponseHandler;
import com.example.qhhq.http.PersistentCookieStore;
import com.example.qhhq.http.RequestParams;


/**
 * 
 * @Description:网络请求工具类
 * @author: chenyh  
 * @date:   2014-7-30 下午4:55:41   
 *
 */
public class HttpClientUtil {
	
	private static AsyncHttpClient client;
	private static HttpClientUtil mInstance;
	private static Context mContext;
	private static PersistentCookieStore mCookieStore;
	
//	static{
		//设置失败重连次数，链接超时，如果不设置，默认设置10s
//		client = new AsyncHttpClient(); 
//		client.setMaxRetriesAndTimeout(3, 10000); 
//		mCookieStore = new PersistentCookieStore(mContext);
//		client.setCookieStore(mCookieStore);
//	}
	
	private HttpClientUtil(Context context){
		mContext = context.getApplicationContext();
		client = new AsyncHttpClient(); 
		client.setMaxRetriesAndTimeout(3, 10000);
        client.setTimeout(30000);
		mCookieStore = new PersistentCookieStore(mContext);
		client.setCookieStore(mCookieStore);
		
	}
	
	public static AsyncHttpClient getClient(){
		return client;
	}
	
	public static synchronized HttpClientUtil getInstance(Context context){
		if(mInstance == null){
			mInstance = new HttpClientUtil(context);
		}

		return mInstance;
	}
	
	/**
	 * @Description: 通过完整url获取一个string对象
	 * @param: @param url
	 * @param: @param responseHandler      
	 */
	public void get(String url, AsyncHttpResponseHandler responseHandler){
		get(url, null, responseHandler);
	}
	
	/**
	 * @Description: 带参数去获取string对象
	 * @param: @param url
	 * @param: @param params
	 * @param: @param responseHandler      
	 */
	public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
		if(NetUtil.checkNet(mContext, true)){
			client.get(mContext, url, params, responseHandler);
		}else{
			responseHandler.sendFailureMessage();
		}
	}
	
	/**
	 * @Description: 不带参数，获取json对象或�?�数�?
	 * @param: @param url
	 * @param: @param responseHandler      
	 */
	public void get(String url, JsonHttpResponseHandler responseHandler){
		client.get(url, responseHandler);
	}
	
	/**
	 * @Description: 带参数，获取json对象或�?�数�?  
	 * @param: @param url
	 * @param: @param params
	 * @param: @param responseHandler      
	 */
	public void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler){
		client.get(url, params, responseHandler);
	}
	
	/**
	 * 
	 * @Description: 下载数据使用，会返回byte数据
	 * @param: @param url
	 * @param: @param responseHandler      
	 */
	public void get(String url, BinaryHttpResponseHandler responseHandler){
		client.get(url, responseHandler);
	}
	
	public void post(Context context, String url, AsyncHttpResponseHandler responseHandler){
		post(context, url, null, responseHandler);
	}
	
	/**
	 * @Description: post请求
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
		if(NetUtil.checkNet(context, true)){
			client.post(context, url, params, responseHandler);
		}else{
			responseHandler.sendFailureMessage();
		}
	}


}
