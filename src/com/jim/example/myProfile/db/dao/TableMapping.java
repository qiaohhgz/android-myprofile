package com.jim.example.myProfile.db.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-3-1
 * Time: 下午11:33
 * To change this template use File | Settings | File Templates.
 */
public enum TableMapping {
    Profile("profile", "create table profile(_id integer primary key autoincrement," +
            "name text,description text, createDate datetime, disable bit)"),

    SoundTask("SoundTask", "create table SoundTask(_id integer primary key autoincrement," +
            "ring int,profileID int)"),

    TimeEveryDayEvent("timeEveryDayEvent", "create table timeEveryDayEvent(_id integer primary key autoincrement," +
            "hour int,minute int, profileID int)");

    String tableName;
    String createSql;

    TableMapping(String tableName, String createSql) {
        this.tableName = tableName;
        this.createSql = createSql;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getCreateSql() {
        return createSql;
    }
}
