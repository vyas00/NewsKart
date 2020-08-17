package com.example.android.newskart;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    private static final String TAG = "JobSchedulerService";
    private static final String USGS_REQUEST_URL =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=061596553c8c44aa85d0c724d3246163";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    if (jobCancelled) {
                        return;
                    }
                Log.d(TAG, "run: called in thread");
                    NewsQueryUtils newsQueryUtils=new NewsQueryUtils();
                    newsQueryUtils.fetchNewData(USGS_REQUEST_URL);
                Log.d(TAG, "Job finished");
                jobFinished(params, true);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        // this is the best place to call asynctask.cancel().
        //because we need to stop the background work ourselves.
        jobCancelled = true;
        return true;
    }
}
