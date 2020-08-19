package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private static final String TAG = "NewsItemAdapter";


    private final String GLOBAL_URL= "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String SPORTS_URL="https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String BUSINESS_URL="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String HEALTH_URL="https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String ENTERTAINMENT_URL="https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String GAMING_URL="https://newsapi.org/v2/everything?q=gaming&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String TECH_URL="https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=061596553c8c44aa85d0c724d3246163";


    Context context;
    private  ArrayList<NewsItem> news;
    Activity activity;
    private String url;

    public NewsItemAdapter(Context context,Activity activity, ArrayList<NewsItem> news, String url) {
        this.context=context;
        this.news=news;
        this.activity=activity;
        this.url=url;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView tvTitle;
        TextView tvDate;
        ImageView ivNewsImage;
        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle =(TextView)view.findViewById(R.id.title_text_view);
            tvDate = (TextView)view.findViewById(R.id.date_text_view);
            ivNewsImage =(ImageView)view.findViewById(R.id.iv_news_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();

            NewsItem currentNewsItem = news.get(position);
            final String browserUrl=currentNewsItem.getBrowserUrl();
            final  String message=currentNewsItem.getDiscription();
            final String content=currentNewsItem.getContent();
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);

            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Read more", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Uri newUri = Uri.parse(browserUrl);
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newUri);
                            activity.startActivity(websiteIntent);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle(content);
            alert.show();
        }
    }
    @NonNull
    @Override
    public NewsItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.news_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsItemAdapter.ViewHolder holder, int position) {
        final NewsItem currentNewsItem = news.get(position);

        holder.tvTitle.setText(currentNewsItem.getTitle());
        holder.tvDate.setText(getTimeStamp(currentNewsItem.getEpochTime()));

        if(url.equals(GLOBAL_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_news_global);
        else if(url.equals(SPORTS_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_sports);
        else if(url.equals(BUSINESS_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_business);
        else if(url.equals(HEALTH_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_health);
        else if(url.equals(ENTERTAINMENT_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_entertainment);
        else if(url.equals(GAMING_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_game);
        else if(url.equals(TECH_URL))holder.ivNewsImage.setImageResource(R.drawable.ic_tech);

        new DownloadImage(holder.ivNewsImage).execute(currentNewsItem.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public String getTimeStamp(long timeinMillies) {
        String date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.format(new Date(timeinMillies));
        return date;
    }


    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

