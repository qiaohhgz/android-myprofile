package com.jim.example.myProfile;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import com.jim.example.myProfile.db.domain.*;
import com.jim.example.myProfile.bean.ProfileBean;

import java.util.Calendar;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    private static String TAG;

    private Button startService;
    private Button stopService;
    private Button gotoAddActivityBtn, gotoListActivityBtn, gotoEventListActivity, gotoTaskListActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = getClass().getName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();
    }

    private void initializeViews() {
        startService = (Button) findViewById(R.id.startServiceBtn);
        stopService = (Button) findViewById(R.id.stopServiceBtn);
        gotoAddActivityBtn = (Button) findViewById(R.id.gotoAddProfileBtn);
        gotoListActivityBtn = (Button) findViewById(R.id.gotoProfileList);
        gotoEventListActivity = (Button) findViewById(R.id.gotoEventList);
        gotoTaskListActivity = (Button) findViewById(R.id.gotoTaskList);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        gotoAddActivityBtn.setOnClickListener(this);
        gotoListActivityBtn.setOnClickListener(this);
        gotoEventListActivity.setOnClickListener(this);
        gotoTaskListActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startServiceBtn:
                startService();
                break;
            case R.id.stopServiceBtn:
                stopService();
                break;
            case R.id.gotoAddProfileBtn:
                gotoAddProfile();
                break;
            case R.id.gotoProfileList:
                gotoListProfile();
                break;
            case R.id.gotoEventList:
                startActivity(new Intent(this, ListEventActivity.class));
                break;
            case R.id.gotoTaskList:
                startActivity(new Intent(this, ListTaskActivity.class));
                break;
        }
    }

    private void gotoListProfile() {
        Intent i = new Intent(this, ListProfileActivity.class);
        startActivity(i);
    }

    private void gotoAddProfile() {
        Intent i = new Intent(this, AddProfileActivity.class);
        startActivity(i);
    }

    private void startService() {
        Log.i(TAG, "start service...");
        Intent i = new Intent(this, MyService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
    }

    private void stopService() {
        Log.i(TAG, "stop service...");
        stopService(new Intent(this, MyService.class));
    }

//    private void showPickTimeDialog() {
//        Message msg = new Message();
//        msg.what = SHOW_PICK_TIME_DIALOG;
//        dateAndTimeHandler.sendMessage(msg);
//    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case TIME_DIALOG_ID:
//                if (timeEvent != null) {
//                    mHour = timeEvent.getHour();
//                    mMinute = timeEvent.getMinute();
//                } else {
//                    mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//                    mMinute = Calendar.getInstance().get(Calendar.MINUTE);
//                }
//                return new TimePickerDialog(this, timeSetListener, mHour, mMinute, true);
//        }
//        return null;
//    }

//    Handler dateAndTimeHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SHOW_PICK_TIME_DIALOG:
//                    showDialog(TIME_DIALOG_ID);
//                    break;
//            }
//        }
//    };
}
