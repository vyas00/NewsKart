package com.example.android.newskart;

import android.content.BroadcastReceiver;
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
                 Log.d(TAG,action + " received");
               JobSharedPreference.setContext(context);
               if(System.currentTimeMillis()- JobSharedPreference.getPreviousLongTime()<30*60*100)
               {

               }


        }
    }
