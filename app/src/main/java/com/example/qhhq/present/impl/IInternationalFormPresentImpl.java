package com.example.qhhq.present.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.Util.Contants;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.bean.InternationalForm;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.http.RequestParams;
import com.example.qhhq.present.IInternationalFormPresent;
import com.example.qhhq.view.IInternationalFormView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public class IInternationalFormPresentImpl implements IInternationalFormPresent {

    private Context context;
    private IInternationalFormView internationalFormView;

    public IInternationalFormPresentImpl(IInternationalFormView internationalFormView, Context context) {
        this.context = context;
        this.internationalFormView = internationalFormView;
    }

    @Override
    public void getInfoList(int markId, int num, int tagId) {
        String url = Contants.GUO_JI_HTTP_BASE;

        RequestParams params = new RequestParams();
        params.put("markid",markId );
        params.put("num",num);
        params.put("tagid",tagId);

        HttpClientUtil.getInstance(context).post(context,url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<InternationalForm> beanList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
//                        JSONObject root = new JSONObject(Info.getString("root"));
                        JSONArray recordSet = new JSONArray(Info.getString("data"));
                        InternationalForm beanEntity;
                        for (int i = 0; i < recordSet.length(); i++) {
                            JSONObject jsonObject = recordSet.getJSONObject(i);
                            beanEntity = new InternationalForm();

                            String id = jsonObject.optString("id");
                            String url = jsonObject.optString("url");
                            String title = jsonObject.optString("title");
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

                            beanList.add(beanEntity);
                        }
                        internationalFormView.showInfoList(beanList);
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
        String url = Contants.GUO_JI_HTTP_BASE;

        RequestParams params = new RequestParams();
        params.put("markid",markId );
        params.put("num",num);
        params.put("tagid",tagId);

        HttpClientUtil.getInstance(context).post(context,url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<InternationalForm> beanList= new ArrayList<>();
                String string = new String(responseBody);
                try {
                    JSONObject Info = new JSONObject(string);
                    if (Info != null) {
//                        JSONObject root = new JSONObject(Info.getString("root"));
                        JSONArray recordSet = new JSONArray(Info.getString("data"));
                        InternationalForm beanEntity;
                        for (int i = 0; i < recordSet.length(); i++) {
                            JSONObject jsonObject = recordSet.getJSONObject(i);
                            beanEntity = new InternationalForm();

                            String id = jsonObject.optString("id");
                            String url = jsonObject.optString("url");
                            String title = jsonObject.optString("title");
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

                            beanList.add(beanEntity);
                        }
                        internationalFormView.showLoadMoreInfoList(beanList);
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
