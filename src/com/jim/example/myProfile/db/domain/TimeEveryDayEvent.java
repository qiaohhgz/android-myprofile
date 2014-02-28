package com.jim.example.myProfile.db.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public class TimeEveryDayEvent implements Event {
    private int id;
    private int hour;
    private int minute;
    private int profileID;

    public TimeEveryDayEvent() {
    }

    public TimeEveryDayEvent(int minute, int hour) {
        this.minute = minute;
        this.hour = hour;
    }

    @Override
    public boolean check() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }
}
