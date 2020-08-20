package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final String TAG = "CategoryAdapter";

    private final String GLOBAL="Global";
    private final String SPORTS="Sports";
    private final String BUSINESS="Business";
    private final String HEALTH="Health";
    private final String ENTERTAINMENT="Fun";
    private final String GAMING="Gaming";
    private final String TECH="Tech";


    private final String GLOBAL_URL= "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String SPORTS_URL="https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String BUSINESS_URL="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String HEALTH_URL="https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String ENTERTAINMENT_URL="https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String GAMING_URL="https://newsapi.org/v2/everything?q=gaming&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String TECH_URL="https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=061596553c8c44aa85d0c724d3246163";


    Context context;
    private ArrayList<String> categoryList;
    Activity activity;
    private int sl_category_position;

    public CategoryAdapter(Context context, Activity activity, ArrayList<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategoryName;
        ImageView ivCategoryImage;
        LinearLayout linearlayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
            ivCategoryImage = (ImageView) view.findViewById(R.id.iv_category_image);
            linearlayout=(LinearLayout)view.findViewById(R.id.ll_category);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String currentCategory = categoryList.get(position);
         holder.tvCategoryName.setText(currentCategory);

        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl_category_position=position;

                notifyDataSetChanged();

                NewsFragment newsFragment=new NewsFragment(GLOBAL_URL);
                String currentStringItem = categoryList.get(position);


                if(currentStringItem.equals(GLOBAL)) {newsFragment= new NewsFragment(GLOBAL_URL); }
                else if(currentStringItem.equals(SPORTS)) {
                    Log.d(TAG, "onClick: sports clicked"); newsFragment= new NewsFragment(SPORTS_URL); }
                else if(currentStringItem.equals(BUSINESS)) { newsFragment= new NewsFragment(BUSINESS_URL); }
                else if(currentStringItem.equals(HEALTH)) {newsFragment= new NewsFragment(HEALTH_URL); }
                else if(currentStringItem.equals(ENTERTAINMENT)) {newsFragment= new NewsFragment(ENTERTAINMENT_URL); }
                else if(currentStringItem.equals(GAMING)) {newsFragment= new NewsFragment(GAMING_URL);  }
                else if(currentStringItem.equals(TECH)) {newsFragment= new NewsFragment(TECH_URL);  }
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container_news, newsFragment).commit();
            }
        });

        if(position==sl_category_position) holder.tvCategoryName.setTextColor(Color.parseColor("#00aaff"));
        else holder.tvCategoryName.setTextColor(Color.parseColor("#6E6868"));

        if(currentCategory.equals(GLOBAL))holder.ivCategoryImage.setImageResource(R.drawable.ic_global);
        else if(currentCategory.equals(SPORTS))holder.ivCategoryImage.setImageResource(R.drawable.ic_sports);
        else if(currentCategory.equals(BUSINESS))holder.ivCategoryImage.setImageResource(R.drawable.ic_business);
        else if(currentCategory.equals(HEALTH))holder.ivCategoryImage.setImageResource(R.drawable.ic_health);
        else if(currentCategory.equals(ENTERTAINMENT)){holder.ivCategoryImage.setImageResource(R.drawable.ic_entertainment); }
        else if(currentCategory.equals(GAMING)){holder.ivCategoryImage.setImageResource(R.drawable.ic_game); }
        else if(currentCategory.equals(TECH)){holder.ivCategoryImage.setImageResource(R.drawable.ic_tech); }
        else{}
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

}


