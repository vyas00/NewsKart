package com.example.android.newskart;

import android.media.Rating;

public class New {

    private String nTile;

    private String nDiscription;

    private String nDate;

    private String nContent;

    private String nBrowserUrl;

     public New(){}

    public New (String Tilte, String Discription, String Date, String Content, String BrowserUrl)
    {
        nTile=Tilte;
        nDiscription=Discription;
        nDate=Date;
        nContent=Content;
        nBrowserUrl=BrowserUrl;

    }

    public String getTitle()
    {
        return nTile;
    }

    public String getDiscription()
    {
        return nDiscription;
    }

    public String getDate()
    {
        return nDate;
    }

    public String getContent()
    {
        return nContent;
    }

    public String getBrowserUrl()
    {
        return nBrowserUrl;
    }

}
