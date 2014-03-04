package com.jim.example.myProfile.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd hh:mm:ss";

    public static Date getDate(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static boolean beforeDate(Date early, Date late) {
        return early.before(late);
    }

    public static boolean afterDate(Date early, Date late) {
        return early.after(late);
    }

    public static boolean dateInDateRange(Date early, Date date, Date late) {
        return date.after(early) && date.before(late);
    }

    public static void main(String[] args) {
        // -- test compare date
        Calendar now = Calendar.getInstance();
        Calendar early = Calendar.getInstance();
        early.add(Calendar.DAY_OF_YEAR, -5);
        Calendar late = Calendar.getInstance();
        late.add(Calendar.DAY_OF_YEAR, 5);
        assertion(DateUtils.beforeDate(early.getTime(), late.getTime()));
        assertion(DateUtils.beforeDate(late.getTime(), early.getTime()), false);

        assertion(DateUtils.afterDate(late.getTime(), early.getTime()));

        // -- test date in date range
        assertion(DateUtils.dateInDateRange(early.getTime(), now.getTime(), late.getTime()));

        // -- test get date ( hour , minute )
        Date date = getDate(17, 23);
        String format = DateFormat.getInstance().format(date);
        System.out.println(format);
    }

    private static void assertion(boolean b) {
        assertion(b, true);
    }

    private static void assertion(boolean b1, boolean b2) {
        if (b1 != b2) {
            Exception ex = new Exception();
            ex.printStackTrace();
        }
    }
}
