package com.jim.example.myProfile.facade;

import android.content.ContentValues;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.TableMapping;

/**
 * JimQiao
 * 14-3-13 下午5:10
 */
public class HistoryFacade implements ILog {
    private DBHelper dbHelper;

    public HistoryFacade(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insert(int level, String tag, String msg) {
        ContentValues values = new ContentValues();
        values.put("level", level);
        values.put("tag", tag);
        values.put("msg", msg);
        dbHelper.getWritableDatabase().insert(TableMapping.History.getTableName(), null, values);
    }

    @Override
    public void v(String tag, String msg) {
        insert(VERBOSE, tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        insert(DEBUG, tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        insert(INFO, tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        insert(WARN, tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        insert(ERROR, tag, msg);
    }
}
