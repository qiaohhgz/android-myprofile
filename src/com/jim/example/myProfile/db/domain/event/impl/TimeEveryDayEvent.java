package com.jim.example.myProfile.db.domain.event.impl;

import android.content.Context;
import android.util.Log;
import com.jim.example.myProfile.db.domain.event.Event;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public class TimeEveryDayEvent implements Event {
    private static final String TAG = TimeEveryDayEvent.class.getName();
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
    public boolean check(Context context) {
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Log.d(TAG, String.format("%s:%s", hourOfDay, minute));
        boolean result = (hourOfDay == hour && minute == minute);
        Log.d(TAG, "Check Result = " + result);
        return result;
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
