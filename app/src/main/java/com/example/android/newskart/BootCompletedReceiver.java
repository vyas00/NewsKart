package com.example.android.newskart;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompletedReceiver";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,action+" received");
        if(action != null) {
            if (action.equals(Intent.ACTION_BOOT_COMPLETED) ) {
                ComponentName componentJobName = new ComponentName(context, JobSchedulerService.class);
                JobInfo info = new JobInfo.Builder(100, componentJobName)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .setPeriodic(3 * 60 * 60 * 1000)
                        .build();
                JobScheduler scheduler = (JobScheduler) context.getSystemService(context.JOB_SCHEDULER_SERVICE);
                int resultCode = scheduler.schedule(info);
                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "Job scheduled");
                } else {
                    Log.d(TAG, "Job scheduling failed");
                }

            }

        }
    }
}
