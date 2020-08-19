package com.example.android.newskart;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    private NewsItemAdapter newsAdapter;
    private ArrayList<NewsItem> newsArrayList;
    RecyclerView newsRecyclerView;
    DatabaseHandler db;
    public String requestUrl;

    public NewsFragment(String url) {
        requestUrl=url;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewNewsFragment = inflater.inflate(R.layout.fragment_news, container, false);
          db=new DatabaseHandler(getContext());


        newsRecyclerView = (RecyclerView) viewNewsFragment.findViewById(R.id.recycler_list_news);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsArrayList =new ArrayList<NewsItem>();

        NewAsyncTask task = new NewAsyncTask();

          task.execute(requestUrl);

        return viewNewsFragment;
    }

    private class NewAsyncTask extends AsyncTask<String, Void, List<NewsItem>> {


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<NewsItem> doInBackground(String... urls) {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            NewsQueryUtils newsQueryUtils=new NewsQueryUtils(getContext());
            List<NewsItem> result = newsQueryUtils.fetchNewData(urls[0]);
            if(result==null) result=db.getAllNews();;
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsItem> data) {
            newsArrayList.clear();

            if (data != null && !data.isEmpty()) {
                newsArrayList= (ArrayList<NewsItem>) data;
                Log.d(TAG, "onPostExecute: "+ db.getNewsCount()+ " no of news items");
                newsAdapter = new NewsItemAdapter(getContext(), getActivity(), newsArrayList,requestUrl);
                newsRecyclerView.setAdapter(newsAdapter);
            }
        }
    }

    private static void setURLQUerry(String querry)
    {

    }
}