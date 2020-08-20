package com.example.android.newskart;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    private NewsItemAdapter newsAdapter;
    private ArrayList<NewsItem> newsArrayList;
    RecyclerView newsRecyclerView;
    DatabaseHandler db;
    public String requestUrl;
    public String tableName;
    public SwipeRefreshLayout newsSwipeToUpdateLayout;

    public NewsFragment(String url,String table) {
        requestUrl=url;
        tableName=table;
    }
    public NewsFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewNewsFragment = inflater.inflate(R.layout.fragment_news, container, false);
          db=new DatabaseHandler(getContext());
          newsSwipeToUpdateLayout=viewNewsFragment.findViewById(R.id.swipe_refresh_news);


        newsRecyclerView = (RecyclerView) viewNewsFragment.findViewById(R.id.recycler_list_news);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsArrayList =new ArrayList<NewsItem>();

        newsArrayList=db.getAllNews(tableName);
        newsAdapter = new NewsItemAdapter(getContext(), getActivity(), newsArrayList,requestUrl);
        newsRecyclerView.setAdapter(newsAdapter);


        newsSwipeToUpdateLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        NewAsyncTask task = new NewAsyncTask();
                         task.execute(requestUrl);

                    }
                }
        );


        return viewNewsFragment;
    }

    private class NewAsyncTask extends AsyncTask<String, Void, Void> {


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(String... urls) {


            if (urls.length < 1 || urls[0] == null) {
               return null;
            }
            NewsQueryUtils newsQueryUtils=new NewsQueryUtils(getContext(),tableName);
             newsQueryUtils.fetchNewData(urls[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getActivity(), "News has been updated!", Toast.LENGTH_LONG).show();
            newsSwipeToUpdateLayout.setRefreshing(false);
            newsArrayList=db.getAllNews(tableName);
            newsAdapter = new NewsItemAdapter(getContext(), getActivity(), newsArrayList,requestUrl);
            newsRecyclerView.setAdapter(newsAdapter);
        }

    }

    public void setSpinningRefreshTrue()
    {
        newsSwipeToUpdateLayout.setRefreshing(true);
    }
    public void setSpinningRefreshFalse()
    {
        newsSwipeToUpdateLayout.setRefreshing(false);
    }


}