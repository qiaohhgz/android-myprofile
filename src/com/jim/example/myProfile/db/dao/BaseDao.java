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
public abstract class BaseDao extends SQLiteOpenHelper {
    private static final String DB_NAME = "myProfile.db";
    private static String TAG;
    private SQLiteDatabase db;

    public BaseDao(Context context, int version) {
        super(context, DB_NAME, null, version);
        TAG = getClass().getName();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        Log.d(TAG, "Create table[" + getTableName() + "]...");
        db.execSQL(getCreateTableSql());
        Log.d(TAG, "");
    }

    public abstract String getTableName();

    public abstract String getCreateTableSql();

    public String getDatabaseName() {
        return DB_NAME;
    }

    public long insert(ContentValues values) {
        db = getWritableDatabase();
        long id = db.insert(getTableName(), null, values);
        return id;
    }

    public void del(int id) {
        db = getWritableDatabase();
        db.delete(getTableName(), "_id=?", new String[]{String.valueOf(id)});
    }

    public void update(ContentValues values, String whereClause, String[] whereArgs) {
        db = getWritableDatabase();
        db.update(getTableName(), values, whereClause, whereArgs);
    }

    public Cursor query() {
        db = getReadableDatabase();
        return db.query(getTableName(), null, null, null, null, null, null);
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
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "On Upgrade.");
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());// 如果数据库存在了这个表，那么删除这个表，重新创建
        db.execSQL(getCreateTableSql());
    }
}
