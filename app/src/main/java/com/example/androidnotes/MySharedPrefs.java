package com.example.androidnotes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.util.Log;

public class MySharedPrefs {
    private static final String TAG = "MySharedPrefs";
    private final SharedPreferences prefs;

    MySharedPrefs(Activity activity){
        super();

        Log.d(TAG, "MysharedPrefs: Constructor");
        prefs = activity.getSharedPreferences("MY_PREFS_KEY", Context.MODE_PRIVATE);
    }

    void save(String key, String text){
        Log.d(TAG, "save: " + key + ":" + text);
        Editor editor = prefs.edit();
        editor.putString(key, text);
        editor.apply(); // commit T/F
    }

    String getValue(String key) {
        String text = prefs.getString(key, "");
        Log.d(TAG, "getValue: " + key + " = " + text);
        return text;
    }

    void clearAll() {
        Log.d(TAG, "clearAll: ");
        Editor editor = prefs.edit();
        editor.clear();
        editor.apply(); // commit T/F
    }

    void removeValue(String key) {
        Log.d(TAG, "removeValue: " + key);
        Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply(); // commit T/F
    }
}
