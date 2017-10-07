package com.example.qhhq.present.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.Util.Contants;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.bean.Macroscopic;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.http.RequestParams;
import com.example.qhhq.present.IMacroscopicPresent;
import com.example.qhhq.view.IMacroscopicView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public class IMacroscopicPresentImpl implements IMacroscopicPresent {

    private Context context;
    private IMacroscopicView macroscopicView;

    public IMacroscopicPresentImpl(IMacroscopicView macroscopicView, Context context) {
        this.context = context;
        this.macroscopicView = macroscopicView;
    }

    @Override
    public void getInfoList(int markId, int num, int tagId) {

        String url = Contants.MACROSCOPIC_HTTP_BASE;

        RequestParams params = new RequestParams();
        params.put("markid",markId );
        params.put("num",num);
        params.put("tagid",tagId);

        HttpClientUtil.getInstance(context).post(context,url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Macroscopic> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
//                        JSONObject root = new JSONObject(Info.getString("root"));
                        JSONArray recordSet = new JSONArray(Info.getString("data"));
                        Macroscopic beanEntity;
                        for (int i = 0; i < recordSet.length(); i++) {
                            JSONObject jsonObject = recordSet.getJSONObject(i);
                            beanEntity = new Macroscopic();

                            String id = jsonObject.optString("id");
                            String title = jsonObject.optString("title");
                            String url = jsonObject.optString("url");
                            String webUrl = jsonObject.optString("weburl");
                            String addTime = jsonObject.optString("addtime");
                            String thumb = jsonObject.optString("thumb");
                            String description = jsonObject.optString("description");
                            String categoryId = jsonObject.optString("category_id");

                            beanEntity.setId(id);
                            beanEntity.setUrl(url);
                            beanEntity.setTitle(title);
                            beanEntity.setWebUrl(webUrl);
                            beanEntity.setAddTime(addTime);
                            beanEntity.setThumb(thumb);
                            beanEntity.setDescription(description);
                            beanEntity.setCategoryId(categoryId);
                            liveBroadCastList.add(beanEntity);
                        }
                        macroscopicView.showInfoList(liveBroadCastList);
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
    public void getLoadMoreInfoList(int markId, int num, int tagId) {

        String url = Contants.MACROSCOPIC_HTTP_BASE;

        RequestParams params = new RequestParams();
        params.put("markid",markId );
        params.put("num",num);
        params.put("tagid",tagId);

        HttpClientUtil.getInstance(context).post(context,url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Macroscopic> liveBroadCastList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
//                        JSONObject root = new JSONObject(Info.getString("root"));
                        JSONArray recordSet = new JSONArray(Info.getString("data"));
                        Macroscopic beanEntity;
                        for (int i = 0; i < recordSet.length(); i++) {
                            JSONObject jsonObject = recordSet.getJSONObject(i);
                            beanEntity = new Macroscopic();

                            String id = jsonObject.optString("id");
                            String title = jsonObject.optString("title");
                            String url = jsonObject.optString("url");
                            String webUrl = jsonObject.optString("weburl");
                            String addTime = jsonObject.optString("addtime");
                            String thumb = jsonObject.optString("thumb");
                            String description = jsonObject.optString("description");
                            String categoryId = jsonObject.optString("category_id");

                            beanEntity.setId(id);
                            beanEntity.setUrl(url);
                            beanEntity.setTitle(title);
                            beanEntity.setWebUrl(webUrl);
                            beanEntity.setAddTime(addTime);
                            beanEntity.setThumb(thumb);
                            beanEntity.setDescription(description);
                            beanEntity.setCategoryId(categoryId);
                            liveBroadCastList.add(beanEntity);
                        }
                        macroscopicView.showLoadMoreInfoList(liveBroadCastList);
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
