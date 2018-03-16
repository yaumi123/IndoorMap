package com.tq.indoormap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.tq.indoormap.greenDao.DaoMaster;
import com.tq.indoormap.greenDao.ShopDao;
import com.tq.indoormap.greenDao.UserDao;

/**
 * Created by niantuo on 2017/3/5.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.DEBUG = true;
        MigrationHelper.migrate(db, UserDao.class, ShopDao.class);
    }
}
