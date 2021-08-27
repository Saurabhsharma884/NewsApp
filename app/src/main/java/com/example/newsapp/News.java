package com.example.newsapp;

public class News {

    private String mTitle;
    private String mImg_url;
    private String mNews_url;
    private String mDesc;

    public News(String title, String img_url, String desc, String news_url) {
        this.mTitle = title;
        this.mImg_url = img_url;
        this.mDesc = desc;
        this.mNews_url = news_url;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmImg_url() {
        return mImg_url;
    }

    public String getmNews_url() {
        return mNews_url;
    }

    public String getmDesc() {
        return mDesc;
    }
}
