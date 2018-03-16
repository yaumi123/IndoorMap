package com.tq.indoormap.global;

import android.app.Application;
import android.content.Context;

import com.tq.imageloader.DefImageLoader;
import com.tq.indoormap.db.GreenDaoUtils;
import com.tq.indoormap.network.API;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by niantuo on 2017/4/2.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        GreenDaoUtils.getSingleTon().initGreenDao();
        DefImageLoader.getDefault().init(this, API.HOST_IMAGE);
        JAnalyticsInterface.init(this);
    }

    public static Context getContext() {
        return context;
    }
}
