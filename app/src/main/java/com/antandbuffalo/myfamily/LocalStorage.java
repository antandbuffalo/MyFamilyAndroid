package com.antandbuffalo.myfamily;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    public static SharedPreferences sharedPreferences;
    static String preferenceName = "MyFamily";

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(preferenceName, 0);
    }

    public static void setItem(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getItem(String key) {
        return sharedPreferences.getString(key, "");
    }

}
