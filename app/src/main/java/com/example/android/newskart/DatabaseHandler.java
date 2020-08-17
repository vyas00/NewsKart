package com.example.android.newskart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsManager.db";
    private static final String TABLE_NEWS = "news";


    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DISCRIPTION = "description";
    private static final String NEWS_DATE = "date";
    private static final String NEWS_URL = "url";
    private static final String NEWS_CONTENT = "content";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " TEXT," + NEWS_CONTENT + " TEXT,"+ NEWS_URL +" TEXT" + ")";
        db.execSQL(CREATE_NEWS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);


        onCreate(db);
    }


    void addNews(NewsItem newsItem) {

        Log.d(TAG, "News is : " + newsItem.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NEWS_TITLE, newsItem.getTitle());
        values.put(NEWS_DISCRIPTION, newsItem.getDiscription());
        values.put(NEWS_DATE, newsItem.getDate());
        values.put(NEWS_CONTENT, newsItem.getContent());
        values.put(NEWS_URL, newsItem.getBrowserUrl());

        db.insert(TABLE_NEWS, null, values);

        db.close();
    }


    public ArrayList<NewsItem> getAllNews() {
        ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();

        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst() && cursor!=null) {
            do {
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(cursor.getString(0));
                newsItem.setDiscription(cursor.getString(1));
                newsItem.setDate(cursor.getString(2));
                newsItem.setContent(cursor.getString(3));
                newsItem.setBrowserUrl(cursor.getString(4));


                newsList.add(newsItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return newsList;

    }

    public void deleteTableNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NEWS);
    }


    public int getNewsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

}
