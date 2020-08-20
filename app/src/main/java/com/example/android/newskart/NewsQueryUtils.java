package com.example.android.newskart;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsQueryUtils {


    private static String tableName;
     private static final String TAG = "NewsQueryUtils";
     private static Context context;
      static DatabaseHandler db;
      private static boolean newNewsAvailable=false;

    public NewsQueryUtils(Context context,String table) {
        this.context=context;
        tableName=table;
    }
     public NewsQueryUtils(Context c){
        context=c;
     }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchNewData(String requestUrl) {

            URL url = createUrl(requestUrl);


            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(TAG, "Problem making the HTTP request.", e);
            }


         extractFeatureFromJson(jsonResponse);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchAllCategoryNewData(ArrayList<String> urlList, ArrayList<String> table) {

        for(int i=0;i<urlList.size();i++) {
             tableName=table.get(i);
            URL url = createUrl(urlList.get(i));
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(TAG, "Problem making the HTTP request.", e);
            }

            extractFeatureFromJson(jsonResponse);
        }
        if(newNewsAvailable)JobUtility.addNotification(context);

    }



    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void extractFeatureFromJson(String newJSON) {
        newNewsAvailable=false;

        if (TextUtils.isEmpty(newJSON)) {
            return;
        }


               List<NewsItem> news = new ArrayList<>();
               db=new DatabaseHandler(context);


        try {

            JSONObject baseJsonResponse = new JSONObject(newJSON);

            JSONArray newArray = baseJsonResponse.getJSONArray("articles");
             if(db.getNewsCount(tableName)==20)
             {db.emptyTableNews(tableName);
                 Log.d(TAG, "extractFeatureFromJson: "+ "empty table successful");}
            for (int i = 0; i < newArray.length(); i++) {

                JSONObject currentNew = newArray.getJSONObject(i);

                String Title = currentNew.getString("title");
                String Description = currentNew.getString("description");
                String Browserurl = currentNew.getString("url");
                String Date = currentNew.getString("publishedAt");
                String Content = currentNew.getString("content");
                String ImageUrl=currentNew.getString("urlToImage");

                NewsItem nnew = new NewsItem(Title, Description, getLongEpochTime(Date) , Content, Browserurl,ImageUrl);
                news.add(nnew);

                if(db.getNewsCount(tableName)==0){db.addNews(nnew,tableName);}
                else if(db.isNewsItemPresent(Browserurl,tableName)==false)
                { newNewsAvailable=true;
                    db.addNews(nnew,tableName);
                    if(db.getNewsCount(tableName)>18) {
                        ArrayList<NewsItem> sortedNewsItem=db.getAllNews(tableName);
                        db.deleteThisNewsItem(sortedNewsItem.get(sortedNewsItem.size() - 1).getBrowserUrl(),tableName);
                    }
                }

            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private static  long getLongEpochTime(String timestring)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date =null;
        try {
            date = df.parse(timestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime();
        return epoch;
    }


}

