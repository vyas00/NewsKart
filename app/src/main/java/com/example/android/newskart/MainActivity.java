package com.example.android.newskart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobScheduler;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private final String GLOBAL_URL= "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String SPORTS_URL="https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String BUSINESS_URL="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String HEALTH_URL="https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String ENTERTAINMENT_URL="https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String GAMING_URL="https://newsapi.org/v2/everything?q=gaming&apiKey=061596553c8c44aa85d0c724d3246163";
    private final String TECH_URL="https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=061596553c8c44aa85d0c724d3246163";

    private static final String TABLE_SPORTS_NEWS = "sports";
    private static final String TABLE_BUSNINESS_NEWS = "business";
    private static final String TABLE_HEALTH_NEWS = "health";
    private static final String TABLE_FUN_NEWS = "fun";
    private static final String TABLE_GAMING_NEWS = "gaming";
    private static final String TABLE_TECH_NEWS = "tech";

    private ArrayList<String> urlList;
    private ArrayList<String> tableList;
    private ProgressBar pbUpateNews;
    DatabaseHandler db;
    private static final String TABLE_GLOBAL_NEWS = "news";
    public String usgsRequestUrl =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlList=new ArrayList<String>();urlList.add(GLOBAL_URL);
        tableList=new ArrayList<>();
        tableList.add(TABLE_GLOBAL_NEWS);tableList.add(TABLE_SPORTS_NEWS);tableList.add(TABLE_BUSNINESS_NEWS);
        tableList.add(TABLE_HEALTH_NEWS);tableList.add(TABLE_FUN_NEWS);tableList.add(TABLE_GAMING_NEWS);tableList.add(TABLE_TECH_NEWS);
        urlList.add(SPORTS_URL);urlList.add(BUSINESS_URL);urlList.add(HEALTH_URL);
        urlList.add(ENTERTAINMENT_URL);urlList.add(GAMING_URL);urlList.add(TECH_URL);
        pbUpateNews = (ProgressBar) findViewById(R.id.pb_updatenews);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_category, new CategoryFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_news, new NewsFragment(usgsRequestUrl,TABLE_GLOBAL_NEWS)).commit();


        JobSharedPreference.setContext(getApplicationContext());

        if(JobSharedPreference.getPreviousLongTime()==null){
            Log.d(TAG, "onCreate: if condition for null entered for the first time");
            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            if(JobUtility.isJobServiceOn(getApplicationContext())) scheduler.cancel(100);
            JobUtility.scheduleNewsJob(getApplicationContext());

        }
        else{
            if(System.currentTimeMillis()-JobSharedPreference.getPreviousLongTime()< 3*60*60*1000) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                if(JobUtility.isJobServiceOn(getApplicationContext())) scheduler.cancel(100);
                JobUtility.scheduleNewsJob(getApplicationContext());
                Log.d(TAG, "onCreate: jobutility called");
            }
        }
        JobSharedPreference.setPreviousLongTime(System.currentTimeMillis());

/*        db=new DatabaseHandler(getApplicationContext());
        db.emptyTableNews();*/

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                NewAsyncTaskToRefeshAll newAsyncTaskToRefeshAll= new NewAsyncTaskToRefeshAll(MainActivity.this);
                newAsyncTaskToRefeshAll.execute();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class NewAsyncTaskToRefeshAll extends AsyncTask<Void, Void, Void> {

        protected MainActivity mainActivity;

        public NewAsyncTaskToRefeshAll(MainActivity mainActivityRef) {
            mainActivity = mainActivityRef;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mainActivity != null) {
                mainActivity.showProgressBar();
            }
        }
        @Override
        protected void onProgressUpdate(Void... progress) {
            super.onProgressUpdate();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            NewsQueryUtils newsQueryUtils=new NewsQueryUtils(getApplicationContext());
            newsQueryUtils.fetchAllCategoryNewData(urlList,tableList);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mainActivity != null) {
                mainActivity.dismissProgressBar();}
            Toast.makeText(MainActivity.this, "News has been updated", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_news, new NewsFragment(usgsRequestUrl,TABLE_GLOBAL_NEWS)).commit();
        }
    }

    private void showProgressBar() {
        pbUpateNews.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBar() {
        pbUpateNews.setVisibility(View.GONE);
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



}
