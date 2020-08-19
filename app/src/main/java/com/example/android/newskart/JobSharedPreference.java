package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class JobSharedPreference {

    Context context;
    private static final String TAG = "JobSharedPreference";

    private static final String LONG_TIME_KEY ="time";


    private static final String JOB_PREF ="job";



    private static SharedPreferences eSharedPref;
    private static SharedPreferences.Editor editor;

    private  JobSharedPreference(){}


    public static void setContext(Context context)
    {
        if(eSharedPref== null && editor==null)
        {    eSharedPref= context.getSharedPreferences(JOB_PREF, Activity.MODE_PRIVATE);
            editor= eSharedPref.edit();
        }

    }


    public static  void setLongTime(Long longTime) {
        editor.putLong(LONG_TIME_KEY,longTime).commit();
    }


    public static Long getLongTime() {
        return eSharedPref.getLong(LONG_TIME_KEY,0);
    }


}
