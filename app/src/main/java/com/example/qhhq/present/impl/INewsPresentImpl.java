package com.example.qhhq.present.impl;

import android.content.Context;
import android.widget.Toast;

import com.example.Util.Contants;
import com.example.Util.HttpClientUtil;
import com.example.qhhq.bean.News;
import com.example.qhhq.http.AsyncHttpResponseHandler;
import com.example.qhhq.present.INewsPresent;
import com.example.qhhq.view.INewsView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus01 on 2017/9/20.
 */

public class INewsPresentImpl implements INewsPresent {

    private Context context;
    INewsView findTypeOneView;

    public INewsPresentImpl(INewsView findTypeOneView, Context context) {
        this.context = context;
        this.findTypeOneView = findTypeOneView;
    }

    @Override
    public void getInfoList() {
        String url = Contants.HTTP_News;

            HttpClientUtil.getInstance(context).get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    {
                        List<News> storeList = new ArrayList<>();    //用于放置每一个for循环后添加的数据
                        String string = new String(responseBody);
                        try {
                            JSONObject Info = new JSONObject(string);
                            if (Info != null) {
                                JSONObject root = new JSONObject(Info.getString("root"));
                                JSONArray recordSet = new JSONArray(root.getString("list"));
                                News newsEntity;
                                for (int i = 0; i < recordSet.length(); i++) {
                                    JSONObject jsonObject = recordSet.getJSONObject(i);
                                    newsEntity = new News();

                                    String id = jsonObject.optString("ID");
                                    String url = jsonObject.optString("url");
                                    String title = jsonObject.optString("title");
                                    String imgLink = jsonObject.optString("imglink");
                                    String date = jsonObject.optString("date");
                                    String sourceName = jsonObject.optString("sourcename");
                                    String content = jsonObject.optString("content168");
                                    String titleSpelling = jsonObject.optString("titlespelling");
                                    String type = jsonObject.optString("TYPE");

                                    newsEntity.setId(id);
                                    newsEntity.setUrl(url);
                                    newsEntity.setTitle(title);
                                    newsEntity.setImgLink(imgLink);
                                    newsEntity.setDate(date);
                                    newsEntity.setSourceName(sourceName);
                                    newsEntity.setContent(content);
                                    newsEntity.setTitleSpelling(titleSpelling);
                                    newsEntity.setType(type);


                                    storeList.add(newsEntity);
                                }
                                findTypeOneView.showInfoList(storeList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "操作失败1", Toast.LENGTH_LONG);
                }
            });
    }
}
