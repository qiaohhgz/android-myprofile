package com.jim.example.myProfile.db.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-3-4
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class TimeRangeEvent implements Event {
    private static final String TAG = TimeRangeEvent.class.getSimpleName();
    private int id;
    private int fromHourOfDay;
    private int fromMinute;
    private int toHourOfDay;
    private int toMinute;
    private int profileID;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromHourOfDay() {
        return fromHourOfDay;
    }

    public void setFromHourOfDay(int fromHourOfDay) {
        this.fromHourOfDay = fromHourOfDay;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(int fromMinute) {
        this.fromMinute = fromMinute;
    }

    public int getToHourOfDay() {
        return toHourOfDay;
    }

    public void setToHourOfDay(int toHourOfDay) {
        this.toHourOfDay = toHourOfDay;
    }

    public int getToMinute() {
        return toMinute;
    }

    public void setToMinute(int toMinute) {
        this.toMinute = toMinute;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    @Override
    public boolean check() {
        return false;  //TODO Writing check method
    }
}
