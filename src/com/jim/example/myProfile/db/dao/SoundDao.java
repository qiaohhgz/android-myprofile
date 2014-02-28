package com.jim.example.myProfile.db.dao;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-27
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class SoundDao extends BaseDao {
    private static final String TABLE_NAME = "sound";
    private static final String CREATE_TABLE_SQL = "create table sound(_id integer primary key autoincrement," +
            "ring int,profileID int)";
    private static int TABLE_VERSION = 2;

    public SoundDao(Context context) {
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
