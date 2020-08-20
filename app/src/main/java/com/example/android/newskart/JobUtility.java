package com.example.android.newskart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class JobUtility {


    private static final String TAG = "JobUtility";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public static void scheduleNewsJob(Context context)
    {
        Log.d(TAG, "scheduleNewsJob: from utility called");
        ComponentName componentJobName = new ComponentName(context, JobSchedulerService.class);
        JobInfo info = new JobInfo.Builder(100, componentJobName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(3*60*60*1000)
                .setOverrideDeadline(10*60*1000)
                .build();
        JobScheduler scheduler = (JobScheduler) context.getSystemService(context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isJobServiceOn(Context context ) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == 100 ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled ;
    }

    public static void addNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.O)
        {
            NotificationChannel channel= new NotificationChannel("channel_id_1", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"channel_id_1")
                        .setSmallIcon(R.drawable.ic_news_global)
                        .setContentTitle("New updates to read")
                        .setContentText("Tap to visit new updates")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0, builder.build());

        Log.d(TAG, " notification function");
    }

}
