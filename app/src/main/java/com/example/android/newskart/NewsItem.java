package com.example.android.newskart;

public class NewsItem {
    private static final String TAG = "NewsItem";

    private String title;

    private String discription;

    private long epochTime;

    private String content;

    private String browserUrl;


     public NewsItem(){}

    public NewsItem(String title, String discription, long epochTime, String content, String browserUrl)
    {
        this.title=title;
        this.discription=discription;
        this.epochTime = epochTime;
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

    public void setEpochTime(long epochTime){
         this.epochTime = epochTime;
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

    public long getEpochTime()
    {
        return epochTime;
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
