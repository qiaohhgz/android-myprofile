package com.jim.example.myProfile.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-3-4
 * Time: 上午9:15
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    public static String join(String[] values, String mark) {
        StringBuffer sb = new StringBuffer();
        if (values == null || values.length == 0) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        }
        sb.append(values[0]);
        for (int i = 1; i < values.length; i++) {
            sb.append(mark);
            sb.append(values[i]);
        }
        return sb.toString();
    }
}
