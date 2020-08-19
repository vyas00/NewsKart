package com.example.android.newskart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
        JobUtility.scheduleNewsJob(getApplicationContext());
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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

    private void addNotification() {
        Intent intent = new Intent(JobSchedulerService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(JobSchedulerService.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.O)
        {
            NotificationChannel channel= new NotificationChannel("channel_id_1", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"channel_id_1")
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle("New updates to read")
                        .setContentText("Tap to visit new updates")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, builder.build());

        Log.d(TAG, " notification function");
    }
}
