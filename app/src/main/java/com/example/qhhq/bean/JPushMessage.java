package com.example.qhhq.bean;

/**
 * Created by asus01 on 2017/9/30.
 */
public class JPushMessage {
    private Long id;
    private String title;
    public static String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        JPushMessage.content = content;
    }

}
