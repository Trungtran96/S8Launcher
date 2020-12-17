package com.launcher.samsung.galaxy.galaxys8launcher.ultils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class SharedPreferencesManager {

    private static String KEY_ANIM = "key_anim";
    private static final String KEY_SNACK = "key_snack";
    private static String KEY_COT = "key_cot";
    private static String KEY_HANG = "key_hang";

    public static void setSnack(Context context, String json){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(KEY_SNACK, json).apply();
    }

    public static String getSnack(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_SNACK, Ultils.defaultSnack(context));
    }

    public static void setAnim(Context context, int anim){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(KEY_ANIM, anim).apply();
    }

    public static int getAnim(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_ANIM, 4);
    }

    public static void setHang(Context context, int anim){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(KEY_HANG, anim).apply();
    }

    public static int getHang(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_HANG, 5);
    }

    public static void setCot(Context context, int anim){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(KEY_COT, anim).apply();
    }

    public static int getCot(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_COT, 4);
    }

}
