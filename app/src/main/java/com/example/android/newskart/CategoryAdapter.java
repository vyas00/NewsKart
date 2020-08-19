package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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


    Context context;
    private ArrayList<String> categoryList;
    Activity activity;

    public CategoryAdapter(Context context, Activity activity, ArrayList<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCategoryName;
        ImageView ivCategoryImage;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
            ivCategoryImage = (ImageView) view.findViewById(R.id.iv_category_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String currentCategory = categoryList.get(position);
         holder.tvCategoryName.setText(currentCategory);
        if(currentCategory.equals(GLOBAL))holder.ivCategoryImage.setImageResource(R.drawable.ic_global);
        else if(currentCategory.equals(SPORTS))holder.ivCategoryImage.setImageResource(R.drawable.ic_sports);
        else if(currentCategory.equals(BUSINESS))holder.ivCategoryImage.setImageResource(R.drawable.ic_business);
        else if(currentCategory.equals(HEALTH))holder.ivCategoryImage.setImageResource(R.drawable.ic_health);
        else if(currentCategory.equals(ENTERTAINMENT)){holder.ivCategoryImage.setImageResource(R.drawable.ic_entertainment); }
        else if(currentCategory.equals(GAMING)){holder.ivCategoryImage.setImageResource(R.drawable.ic_game); }
    }

    @Override
    public int getItemCount() {
        return this.categoryList.size();
    }

}


