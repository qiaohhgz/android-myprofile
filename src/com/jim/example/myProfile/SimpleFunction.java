package com.jim.example.myProfile;

import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-3-3
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */
public class SimpleFunction implements Function{
    @Override
    public void run(TextView v, String text) {
        v.setText(text);
    }
}
