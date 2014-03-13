package com.jim.example.myProfile.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-27
 * Time: 下午6:07
 * To change this template use File | Settings | File Templates.
 */
public final class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "myProfile.db";
    private static String TAG;
    private static int VERSION = 5;

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        TableMapping[] values = TableMapping.values();
        for (TableMapping tableMapping : values) {
            Log.d(TAG, "Create table[" + tableMapping.getTableName() + "]...");
            db.execSQL(tableMapping.getCreateSql());
            Log.d(TAG, "Create successful.");
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public synchronized void close() {
        super.close();
        if (null != db) {
            db.close();
        }
    }

    /**
     * 更新表的操作
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "On Upgrade.");
        TableMapping[] values = TableMapping.values();
        for (TableMapping tableMapping : values) {
            db.execSQL("DROP TABLE IF EXISTS " + tableMapping.getTableName());// 如果数据库存在了这个表，那么删除这个表，重新创建
            db.execSQL(tableMapping.getCreateSql());
            Log.d(TAG, "Create successful.");
        }
    }
}
