package com.jim.example.myProfile.db.domain;

import android.accounts.OnAccountsUpdateListener;
import com.jim.example.myProfile.util.DateUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeEvent implements Event {
    public static final int START_DATE_AND_END_DATE = 0;
    public static final int ONLY_START_DATE = 1;
    public static final int TIME_EVERY_DAY = 2;

    private int id;
    private Date startDate;
    private Date endDate;
    private int type;

    public TimeEvent() {
    }

    public static TimeEvent buildTimeEveryDay(int hour, int minute) {
        TimeEvent timeEvent = new TimeEvent();
        Date startDate = DateUtils.getDate(hour, minute);
        timeEvent.setStartDate(startDate);
        return timeEvent;
    }

    @Override
    public boolean check() {
        switch (type) {
            case START_DATE_AND_END_DATE:
                return DateUtils.dateInDateRange(this.startDate, new Date(), this.endDate);
            case ONLY_START_DATE:
                return false;
            case TIME_EVERY_DAY:

                return false;
            default:
                return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
