package com.example.ibooks.apkchangeinfo;

import android.content.Context;
import android.content.SharedPreferences;
import de.robv.android.xposed.XSharedPreferences;

public class SharedPrefs {
    private static XSharedPreferences myXsharedPref;
    private SharedPreferences mySharedPref;
    private Context shareContext;

    public SharedPrefs(Context appContext) {
        mySharedPref=appContext.getSharedPreferences("Data_pref", 1);
    }

    public void setSharedPrefs(String key, String value) {
        try {
            this.mySharedPref.edit().putString(key, value).commit();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public String getValue(String key) {
        String value = "";
        try {
            value = this.mySharedPref.getString(key, null);

        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    public static XSharedPreferences getMyXSharedPref() {
        if (myXsharedPref != null) {

            myXsharedPref.reload();
            return myXsharedPref;
        }
        myXsharedPref = new XSharedPreferences("com.example.ibooks.apkchangeinfo", "Data_pref");
        return myXsharedPref;
    }

    public static String getXValue(String key) {
        String value = "";
        try {
            value = getMyXSharedPref().getString(key, null);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }
}