package com.example.android.newskart;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class JobSharedPreference {

    Context context;
    private static final String TAG = "JobSharedPreference";

/*    private static final String LONG_CURRENT_TIME_KEY ="time";*/
    private static final String LONG_PREVIOUS_TIME_KEY ="ptime";


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

/*
    public static  void setCurrentLongTime(Long longTime) {
        editor.putLong(LONG_CURRENT_TIME_KEY,longTime).commit();
    }


    public static Long getCurrentLongTime() {
        return eSharedPref.getLong(LONG_CURRENT_TIME_KEY,0);
    }
*/


    public static  void setPreviousLongTime(Long longTime) {
        editor.putLong(LONG_PREVIOUS_TIME_KEY,longTime).commit();
    }


    public static Long getPreviousLongTime() {
        return eSharedPref.getLong(LONG_PREVIOUS_TIME_KEY,0);
    }

}
