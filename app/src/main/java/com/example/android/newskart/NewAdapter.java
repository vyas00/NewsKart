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


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {

    private static final String LOCATION_SEPARATOR = "T";
    private static final String SEPARATOR = "Z";
    private static final String SEPARAT = "GMT+";
    private static final String TAG = "NewAdapter";

    Context context;
    private  ArrayList<New> news;

    public NewAdapter(Context context, ArrayList<New> news) {
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
            New currentNew = news.get(position);
            MainActivity.setContentAfterVisibility(currentNew.getContent());
            mainActivity.returnUrl(currentNew.getBrowserUrl());
        }
    }
    @NonNull
    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.news_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final NewAdapter.ViewHolder holder, int position) {
        final New currentNew = news.get(position);

        holder.titleView.setText(currentNew.getTitle());

        String Ddate;
        String Ttime;
        String originalTime;
        String datetime;

        String displayt;
        String displayd;

        String mixdatetime = currentNew.getDate();
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

