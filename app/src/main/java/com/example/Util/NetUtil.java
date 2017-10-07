package com.example.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 网络连接方法
 * @author: chenyh
 * @Date: 2014-9-9上午10:03:21
 */
public class NetUtil {

	public static boolean checkNet(Context context) {
		return checkNet(context, false);
	}

	public static boolean checkNet(Context context, boolean showToast) {
		boolean conn = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			conn = netInfo != null && netInfo.isConnectedOrConnecting();
		} catch (Exception e) {
			conn = false;
		}
		if (showToast && !conn) {
//			UIHelper.ToastUtil(context, R.string.toast_no_network);
			
		}
		return conn;
	}

	//  URL 是否有效
	public static boolean checkURL(String url) {
		boolean value = false;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			int code = conn.getResponseCode();
			value = code == 200;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	// 判断wifi是否可用
	public static boolean isWiFiActive(Context inContext) {
		Context context = inContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return info != null && info.getState() == State.CONNECTED;
	}

	/**
	 * 获取网络连接的类
	 * 
	 * @return
	 */
	public static String getConnectType(Context context) {
		String type = null;
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		String typeStr = null;
		if(info!=null){
			typeStr = info.getExtraInfo();
		}
		if (TextUtils.isEmpty(typeStr)) {
			type = "WIFI";
		} else {
			type = typeStr;
		}
		return type;
	}

	/**
	 * 获取Ip地址
	 * 
	 * @return
	 */
	public static String getHostIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
						.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}
	public enum NetType{
		MOBILE,WIFI
	}
	public interface NetChangedListener{
		void onNetConnected(NetType type);
	}
	
	/**
	 * 端口号是否被占用
	 * @param port 端口
	 */
	public static boolean isLocalPortInUse(int port){
		boolean isUsed = false;
		try {
			isUsed = isPortInUse("127.0.0.1", port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return isUsed;
	} 
	
	/**
     * @brief 查主机端口是否被占用
     * @param host 主机
     * @param port 端口
     * @return true: already in use, false: not.
     * @throws UnknownHostException
     */
    private static boolean isPortInUse(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress, port);
            socket.close();
            flag = true;
        } catch (IOException e) {
        }
        return flag;
    }
	
}
