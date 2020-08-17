package com.example.android.newskart;

import android.media.Rating;

public class NewsItem {
    private static final String TAG = "NewsItem";

    private String title;

    private String discription;

    private String date;

    private String content;

    private String browserUrl;

     public NewsItem(){}

    public NewsItem(String title, String discription, String date, String content, String browserUrl)
    {
        this.title=title;
        this.discription=discription;
        this.date=date;
        this.content=content;
        this.browserUrl=browserUrl;

    }

    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setDiscription(String discription){
         this.discription=discription;
    }

    public void setDate(String date){
         this.date=date;
    }

    public void setContent(String content){
         this.content=content;
    }
    public void setBrowserUrl(String browserUrl){
         this.browserUrl=browserUrl;
    }
    public String getTitle()
    {
        return title;
    }

    public String getDiscription()
    {
        return discription;
    }

    public String getDate()
    {
        return date;
    }

    public String getContent()
    {
        return content;
    }

    public String getBrowserUrl()
    {
        return browserUrl;
    }

}
