package com.jim.example.myProfile.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-27
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class ProfileDao extends BaseDao{
    private static final String TABLE_NAME = "profile";
    private static final String CREATE_TABLE_SQL = "create table profile(_id integer primary key autoincrement," +
            "name text,description text, createDate datetime, disable bit)";
    private static int TABLE_VERSION = 2;

    public ProfileDao(Context context) {
        super(context, TABLE_VERSION);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getCreateTableSql() {
        return CREATE_TABLE_SQL;
    }
}
