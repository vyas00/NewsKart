package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private static final String TAG = "NewsItemAdapter";



    Context context;
    private  ArrayList<NewsItem> news;
    Activity activity;

    public NewsItemAdapter(Context context,Activity activity, ArrayList<NewsItem> news) {
        this.context=context;
        this.news=news;
        this.activity=activity;
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
        /*String Ddate;
        String Ttime;
        String originalTime;
        String datetime;

        String displayt;
        String displayd;
*/
/*        String mixdatetime = currentNewsItem.getEpochTime();
        Log.d(TAG, "onBindViewHolder: "+ mixdatetime);*/

/*        String[] parts = mixdatetime.split(LOCATION_SEPARATOR);
        Ddate = parts[0];
        Ttime = parts[1];*/

/*
        String[] part = Ttime.split(SEPARATOR);
        originalTime=part[0];*/

/*        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
*//*        String dateInString = Ddate+" "+originalTime;*//*
        Date date = null;
        try {
            date = sdf.parse(mixdatetime);
           String datetime=date.toString();

*//*            String[] pat= datetime.split(SEPARAT);
           displayd=pat[0];*//*

            holder.dateView.setText(datetime);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/


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

}

