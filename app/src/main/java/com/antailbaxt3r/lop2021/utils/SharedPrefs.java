package com.antailbaxt3r.lop2021.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String
        uid = "uid",
        name = "name";

    private String TAG = "SharedPrefs";

    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUID() {
        return sharedPreferences.getString(uid, null);
    }

    public void saveUID(String key){
        editor.putString(uid, key);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(name, null);
    }

    public void saveName(String key){
        editor.putString(name, key);
        editor.commit();
    }
}
