package com.jim.example.myProfile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.jim.example.myProfile.Function;

/**
 * JimQiao
 * 14-3-13 下午12:01
 */
public class ProfileAdapter extends SimpleCursorAdapter {
    private Function[] functions;
    private int index;

    public ProfileAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    public ProfileAdapter(Context context, int layout, Cursor c, String[] from, int[] to, Function[] functions) {
        super(context, layout, c, from, to);
        this.functions = functions;
    }

    @Override
    public void setViewText(TextView v, String text) {
        if (functions != null) {
            Function fun = functions[index];
            fun.run(v, text);
            boolean isLast = index == (functions.length - 1);
            if (isLast) {
                index = 0;
            } else {
                index++;
            }
        } else {
            super.setViewText(v, text);
        }
    }
}
