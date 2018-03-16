package com.tq.indoormap.utils;

import android.widget.Toast;

import com.tq.indoormap.global.MyApplication;

/**
 * Created by niantuo on 2017/4/4.
 */

public class ToastUtils {
    public static void toast(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
