package com.example.newsapp;

public class News {

    String title;
    String img_url;
    String desc;

    public News(String title, String img_url, String desc) {
        this.title = title;
        this.img_url = img_url;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getDesc() {
        return desc;
    }
}
