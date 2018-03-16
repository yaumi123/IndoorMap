package com.tq.indoormap.global;

import android.content.Context;
import android.content.SharedPreferences;

import com.tq.indoormap.location.LocationManager;

/**
 * Created by niantuo on 2017/4/11.
 */

public class Config {

    final static String TYPE = "key_ble_type";
    final static String SHARE_NAME = "sharedprefrences_name";

    public static void keepType(Context context, int type) {
        SharedPreferences preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        preferences.edit().putInt(TYPE, type).apply();
    }

    public static int getType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TYPE, LocationManager.Type.TYPE_2);
    }
}
