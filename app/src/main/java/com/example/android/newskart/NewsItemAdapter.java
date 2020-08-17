package com.example.android.newskart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private static final String LOCATION_SEPARATOR = "T";
    private static final String SEPARATOR = "Z";
    private static final String SEPARAT = "GMT+";
    private static final String TAG = "NewsItemAdapter";

    Context context;
    private  ArrayList<NewsItem> news;

    public NewsItemAdapter(Context context, ArrayList<NewsItem> news) {
        this.context=context;
        this.news=news;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView titleView;
        TextView dateView;
        public ViewHolder(@NonNull View view) {
            super(view);
            titleView=(TextView)view.findViewById(R.id.title_text_view);
            dateView = (TextView)view.findViewById(R.id.date_text_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MainActivity mainActivity=new MainActivity();
            mainActivity.setvisibilityChanges();
            int position=getAdapterPosition();
            NewsItem currentNewsItem = news.get(position);
            MainActivity.setContentAfterVisibility(currentNewsItem.getContent());
            mainActivity.returnUrl(currentNewsItem.getBrowserUrl());
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

        holder.titleView.setText(currentNewsItem.getTitle());

        String Ddate;
        String Ttime;
        String originalTime;
        String datetime;

        String displayt;
        String displayd;

        String mixdatetime = currentNewsItem.getDate();
        Log.d(TAG, "onBindViewHolder: "+ mixdatetime);

        String[] parts = mixdatetime.split(LOCATION_SEPARATOR);
        Ddate = parts[0];
        Ttime = parts[1];


        String[] part = Ttime.split(SEPARATOR);
        originalTime=part[0];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInString = Ddate+" "+originalTime;
        Date date = null;
        try {
            date = sdf.parse(dateInString);

            Date d=new Date();
            datetime=date.toString();

            String[] pat= datetime.split(SEPARAT);
            displayd=pat[0];
            displayt=pat[1];

            holder.dateView.setText(displayd);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }


}

