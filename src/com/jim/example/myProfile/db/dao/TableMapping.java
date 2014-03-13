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
            "name varchar(50),description varchar(100), createDate datetime, disable bit)"),

    SoundTask("SoundTask", "create table SoundTask(_id integer primary key autoincrement," +
            "ring tinyint,alarm tinyint,music tinyint,voiceCall tinyint,profileID int)"),

    TimeEveryDayEvent("timeEveryDayEvent", "create table timeEveryDayEvent(_id integer primary key autoincrement," +
            "hour tinyint,minute tinyint, profileID int)"),

    TimeRangeEvent("timeRangeEvent", "create table timeRangeEvent(_id integer primary key autoincrement," +
            "fromHourOfDay tinyint,fromMinute tinyint,toHourOfDay tinyint,toMinute tinyint, desc varchar(100), profileID int)"),

    History("history", "create table history(_id integer primary key autoincrement," +
                           "level tinyint,tag varchar(100),msg text)");

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
