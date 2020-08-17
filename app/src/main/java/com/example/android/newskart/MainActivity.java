package com.example.android.newskart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   public  String browserUrl;

    private static final String TAG = "MainActivity";



    private static final String USGS_REQUEST_URL =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";

    private NewAdapter newsAdapter;
    private ArrayList<New> newsArrayList;
    RecyclerView newsRecyclerView;

    public  static TextView tvContentChange;
     public static View btnBackButton;
     public  static View btnBrowserButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContentChange = (TextView)findViewById(R.id.tv_content);
        btnBackButton =findViewById(R.id.btn_back);
        btnBrowserButton =findViewById(R.id.btn_browser);

        final SearchView svSearchBox = (SearchView) findViewById(R.id.sv_searchbox);


        svSearchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        });

         newsRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

          newsArrayList =new ArrayList<New>();
           NewAsyncTask task = new NewAsyncTask();
          task.execute(USGS_REQUEST_URL);


        Button browser=(Button)findViewById(R.id.btn_browser) ;
        browser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Uri newUri = Uri.parse(browserUrl);

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newUri);
                startActivity(websiteIntent);

            }
        });


/*        newRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                New currentnew = mAdapter.getItem(position);
                TextView contentchange= (TextView)findViewById(R.id.content_text_view);
                contentchange.setVisibility(View.VISIBLE);
                contentchange.setText(currentnew.getContent());
                View backbutton=findViewById(R.id.back_button);
                backbutton.setVisibility(View.VISIBLE);

                View browserbutton=findViewById(R.id.browser_button);
                browserbutton.setVisibility(View.VISIBLE);
                ReturnUrl(currentnew.getBrowserUrl());

            }
        });*/



    }
public void clearText(View view)
{
BackbuttonCalling();

}
public void setvisibilityChanges()
{
    tvContentChange.setVisibility(View.VISIBLE);
    btnBackButton.setVisibility(View.VISIBLE);
    btnBrowserButton.setVisibility(View.VISIBLE);
}
public static void setContentAfterVisibility(String input)
{
    tvContentChange.setText(input);
}
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


    private class NewAsyncTask extends AsyncTask<String, Void, List<New>> {


        @Override
        protected List<New> doInBackground(String... urls) {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<New> result = QueryUtils.fetchNewData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<New> data) {
            newsArrayList.clear();

            if (data != null && !data.isEmpty()) {
                newsArrayList = (ArrayList<New>) data;
                newsAdapter = new NewAdapter(MainActivity.this, newsArrayList);
                newsRecyclerView.setAdapter(newsAdapter);
            }
        }


    }


    private void BackbuttonCalling(){

TextView content= (TextView)findViewById(R.id.tv_content);
content.setText(null);
content.setVisibility(View.GONE);
        View backbutton=findViewById(R.id.btn_back);
        backbutton.setVisibility(View.GONE);
        View onchrome=findViewById(R.id.btn_browser);
        onchrome.setVisibility(View.GONE);

    }

public  void returnUrl(String url)
{
 browserUrl=url;
}

}
