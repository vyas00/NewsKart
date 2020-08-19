package com.example.android.newskart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.news_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Global"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new NewsFragment()).commit();



/*        final SearchView svSearchBox = (SearchView) findViewById(R.id.sv_searchbox);*/




/*        svSearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String inputUrl= "https://newsapi.org/v2/everything?q="+query+"&apiKey=061596553c8c44aa85d0c724d3246163";
                NewAsyncTask task = new NewAsyncTask();
                task.execute(inputUrl);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/



    }
/*

public void tech(View view)
{
    String inputUrl= "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=061596553c8c44aa85d0c724d3246163";

    NewAsyncTask task = new NewAsyncTask();
    task.execute(inputUrl);

}

    public void business(View view)
    {
        String inputUrl= "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void sports(View view)
    {
        String inputUrl= "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void health(View view)
    {
        String inputUrl= "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void covid(View view)
    {
        String inputUrl= "https://newsapi.org/v2/everything?q=corona&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void entertainment(View view)
    {
        String inputUrl= "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void bollywood(View view)
    {
        String inputUrl= "https://newsapi.org/v2/everything?q=bollywood&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void startup(View view)
    {
        String inputUrl= "https://newsapi.org/v2/everything?q=startup&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }

    public void food(View view)
    {
        String inputUrl= "https://newsapi.org/v2/everything?q=food&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }
    public void travel(View view)
    {
        String inputUrl= "https://newsapi.org/v2/everything?q=travel&apiKey=061596553c8c44aa85d0c724d3246163";

        NewAsyncTask task = new NewAsyncTask();
        task.execute(inputUrl);

    }
*/


/*    private class NewAsyncTask extends AsyncTask<String, Void, List<NewsItem>> {


        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<NewsItem> doInBackground(String... urls) {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            NewsQueryUtils newsQueryUtils=new NewsQueryUtils(getApplicationContext());
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
                newsAdapter = new NewsItemAdapter(MainActivity.this, newsArrayList);
                newsRecyclerView.setAdapter(newsAdapter);
            }
        }


    }*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ScheduleJob()
    {
        ComponentName componentJobName = new ComponentName(this, JobSchedulerService.class);
        JobInfo info = new JobInfo.Builder(100, componentJobName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(3 * 60 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

}
