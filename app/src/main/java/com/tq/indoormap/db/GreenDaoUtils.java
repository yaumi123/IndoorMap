package com.tq.indoormap.db;

import android.database.sqlite.SQLiteDatabase;

import com.tq.indoormap.global.MyApplication;
import com.tq.indoormap.greenDao.DaoMaster;
import com.tq.indoormap.greenDao.DaoSession;

/**
 * Created by niantuo on 2016/11/9.
 */

public class GreenDaoUtils {


    final String DB_NAME = "IndoorMap";

    private MySQLiteOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mMaster;
    private DaoSession mSession;

    private static GreenDaoUtils daoUtils;

    public static GreenDaoUtils getSingleTon() {
        if (daoUtils == null) {
            daoUtils = new GreenDaoUtils();
        }
        return daoUtils;
    }

    public GreenDaoUtils() {
        initGreenDao();
    }


    public synchronized void initGreenDao() {
        if (mHelper == null)
            mHelper = new MySQLiteOpenHelper(MyApplication.getContext(), DB_NAME);
        if (db == null)
            db = mHelper.getWritableDatabase();
        if (mMaster == null)
            mMaster = new DaoMaster(db);
        if (mSession == null)
            mSession = mMaster.newSession();
    }

    public DaoMaster getMaster() {
        return mMaster;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DaoSession getSession() {
        return mSession;
    }
}
