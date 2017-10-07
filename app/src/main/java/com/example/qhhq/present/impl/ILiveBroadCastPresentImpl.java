package com.example.qhhq.present.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.Util.Contants;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.bean.LiveBroadCast;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.http.RequestParams;
import com.example.qhhq.present.ILiveBroadCastPresent;
import com.example.qhhq.view.ILiveBroadCastView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public class ILiveBroadCastPresentImpl implements ILiveBroadCastPresent{

    private Context context;
    ILiveBroadCastView liveBroadCastView;

    public ILiveBroadCastPresentImpl(ILiveBroadCastView liveBroadCastView, Context context) {
        this.context = context;
        this.liveBroadCastView = liveBroadCastView;
    }

    @Override
    public void getFinanceLiveBroadCastInfoList(int cId, int markId, int num) {
        String url = Contants.HTTP_TXT +Contants.HTTP_ZB;

        RequestParams params = new RequestParams();
        params.put("cid",cId );
        params.put("markid",markId);
        params.put("num",num);

        HttpClientUtil.getInstance(context).post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<LiveBroadCast> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
                        if (Info.getString("code").equals("200")) {
//                            JSONObject result = new JSONObject(Info.getString("result"));
                            JSONArray recordSet = new JSONArray(Info.getString("data"));
                            LiveBroadCast liveBroadCastEntity;
                            for (int i = 0; i < recordSet.length(); i++) {
                                JSONObject jsonObject = recordSet.getJSONObject(i);
                                liveBroadCastEntity = new LiveBroadCast();

                                String id = jsonObject.optString("id");
                                String title = jsonObject.optString("title");
                                String pictureUrl = jsonObject.optString("picture");
                                String istoutiao = jsonObject.optString("istoutiao");
                                String playCount = jsonObject.optString("play_count");
                                String publishTime = jsonObject.optString("publish_time");
                                String categoryId = jsonObject.optString("category_id");
                                String url = jsonObject.optString("url");

                                liveBroadCastEntity.setId(id);
                                liveBroadCastEntity.setTile(title);
                                liveBroadCastEntity.setPictureUrl(pictureUrl);
                                liveBroadCastEntity.setIstoutiao(istoutiao);
                                liveBroadCastEntity.setPlayCount(playCount);
                                liveBroadCastEntity.setPublishTime(publishTime);
                                liveBroadCastEntity.setCategoryId(categoryId);
                                liveBroadCastEntity.setUrl(url);

                                liveBroadCastList.add(liveBroadCastEntity);
                            }
                            liveBroadCastView.showFinanceLiveBroadCastInfoList(liveBroadCastList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void getForeignExchangeInfoList(int cId, int markId, int num) {
        String url = Contants.HTTP_TXT +Contants.HTTP_ZB;

        RequestParams params = new RequestParams();
        params.put("cid",cId );
        params.put("markid",markId);
        params.put("num",num);

        HttpClientUtil.getInstance(context).post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<LiveBroadCast> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
                        if (Info.getString("code").equals("200")) {
//                            JSONObject result = new JSONObject(Info.getString("result"));
                            JSONArray recordSet = new JSONArray(Info.getString("data"));
                            LiveBroadCast liveBroadCastEntity;
                            for (int i = 0; i < recordSet.length(); i++) {
                                JSONObject jsonObject = recordSet.getJSONObject(i);
                                liveBroadCastEntity = new LiveBroadCast();

                                String id = jsonObject.optString("id");
                                String title = jsonObject.optString("title");
                                String pictureUrl = jsonObject.optString("picture");
                                String istoutiao = jsonObject.optString("istoutiao");
                                String playCount = jsonObject.optString("play_count");
                                String publishTime = jsonObject.optString("publish_time");
                                String categoryId = jsonObject.optString("category_id");
                                String url = jsonObject.optString("url");

                                liveBroadCastEntity.setId(id);
                                liveBroadCastEntity.setTile(title);
                                liveBroadCastEntity.setPictureUrl(pictureUrl);
                                liveBroadCastEntity.setIstoutiao(istoutiao);
                                liveBroadCastEntity.setPlayCount(playCount);
                                liveBroadCastEntity.setPublishTime(publishTime);
                                liveBroadCastEntity.setCategoryId(categoryId);
                                liveBroadCastEntity.setUrl(url);

                                liveBroadCastList.add(liveBroadCastEntity);
                            }
                            liveBroadCastView.showForeignExchangeInfoList(liveBroadCastList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void getRefreshInfoList(int cId, int markId, int num) {
        String url = Contants.HTTP_TXT +Contants.HTTP_ZB;

        RequestParams params = new RequestParams();
        params.put("cid",cId );
        params.put("markid",markId);
        params.put("num",num);

        HttpClientUtil.getInstance(context).post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<LiveBroadCast> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
                        if (Info.getString("code").equals("200")) {
//                            JSONObject result = new JSONObject(Info.getString("result"));
                            JSONArray recordSet = new JSONArray(Info.getString("data"));
                            LiveBroadCast liveBroadCastEntity;
                            for (int i = 0; i < recordSet.length(); i++) {
                                JSONObject jsonObject = recordSet.getJSONObject(i);
                                liveBroadCastEntity = new LiveBroadCast();

                                String id = jsonObject.optString("id");
                                String title = jsonObject.optString("title");
                                String pictureUrl = jsonObject.optString("picture");
                                String istoutiao = jsonObject.optString("istoutiao");
                                String playCount = jsonObject.optString("play_count");
                                String publishTime = jsonObject.optString("publish_time");
                                String categoryId = jsonObject.optString("category_id");
                                String url = jsonObject.optString("url");

                                liveBroadCastEntity.setId(id);
                                liveBroadCastEntity.setTile(title);
                                liveBroadCastEntity.setPictureUrl(pictureUrl);
                                liveBroadCastEntity.setIstoutiao(istoutiao);
                                liveBroadCastEntity.setPlayCount(playCount);
                                liveBroadCastEntity.setPublishTime(publishTime);
                                liveBroadCastEntity.setCategoryId(categoryId);
                                liveBroadCastEntity.setUrl(url);

                                liveBroadCastList.add(liveBroadCastEntity);
                            }
                            liveBroadCastView.showRefreshInfoList(liveBroadCastList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void getLoadMoreInfoList(int cId, int markId, int num) {
        String url = Contants.HTTP_TXT +Contants.HTTP_ZB;

        RequestParams params = new RequestParams();
        params.put("cid",cId );
        params.put("markid",markId);
        params.put("num",num);

        HttpClientUtil.getInstance(context).post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<LiveBroadCast> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
                        if (Info.getString("code").equals("200")) {
//                            JSONObject result = new JSONObject(Info.getString("result"));
                            JSONArray recordSet = new JSONArray(Info.getString("data"));
                            LiveBroadCast liveBroadCastEntity;
                            for (int i = 0; i < recordSet.length(); i++) {
                                JSONObject jsonObject = recordSet.getJSONObject(i);
                                liveBroadCastEntity = new LiveBroadCast();

                                String id = jsonObject.optString("id");
                                String title = jsonObject.optString("title");
                                String pictureUrl = jsonObject.optString("picture");
                                String istoutiao = jsonObject.optString("istoutiao");
                                String playCount = jsonObject.optString("play_count");
                                String publishTime = jsonObject.optString("publish_time");
                                String categoryId = jsonObject.optString("category_id");
                                String url = jsonObject.optString("url");

                                liveBroadCastEntity.setId(id);
                                liveBroadCastEntity.setTile(title);
                                liveBroadCastEntity.setPictureUrl(pictureUrl);
                                liveBroadCastEntity.setIstoutiao(istoutiao);
                                liveBroadCastEntity.setPlayCount(playCount);
                                liveBroadCastEntity.setPublishTime(publishTime);
                                liveBroadCastEntity.setCategoryId(categoryId);
                                liveBroadCastEntity.setUrl(url);

                                liveBroadCastList.add(liveBroadCastEntity);
                            }
                            liveBroadCastView.showLoadMoreInfoList(liveBroadCastList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
            }
        });

    }
}
