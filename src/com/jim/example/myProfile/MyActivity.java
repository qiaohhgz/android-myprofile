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
    private static final int SHOW_PICK_TIME_DIALOG = 0;
    private static final int TIME_DIALOG_ID = 1;

    private TextView timeView;
    private Button pickTimeBtn;
    private Button startService;
    private Button stopService;
    private Button gotoAddActivityBtn, gotoListActivityBtn;

    private int mHour;
    private int mMinute;
    private ProfileBean profile;
    private TimeEveryDayEvent timeEvent;
    private Task soundTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();

        createProfile();

        //Test
        createSoundTask(buildTestSoundBean());
    }

    private SoundSettingBean buildTestSoundBean() {
        SoundSettingBean soundBean = new SoundSettingBean(5);
        return soundBean;
    }

    private void initializeViews() {
        timeView = (TextView) findViewById(R.id.timeView);
        pickTimeBtn = (Button) findViewById(R.id.pickTimeBtn);
        startService = (Button) findViewById(R.id.startServiceBtn);
        stopService = (Button) findViewById(R.id.stopServiceBtn);
        gotoAddActivityBtn = (Button) findViewById(R.id.gotoAddProfileBtn);
        gotoListActivityBtn = (Button) findViewById(R.id.gotoListProfileBtn);

        pickTimeBtn.setOnClickListener(this);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        gotoAddActivityBtn.setOnClickListener(this);
        gotoListActivityBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickTimeBtn:
                showPickTimeDialog();
                break;
            case R.id.startServiceBtn:
                startService();
                break;
            case R.id.stopServiceBtn:
                stopService();
                break;
            case R.id.gotoAddProfileBtn:
                gotoAddProfile();
                break;
            case R.id.gotoListProfileBtn:
                gotoListProfile();
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
        i("start service...");
        Intent i = new Intent(this, MyService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
    }

    private void stopService() {
        i("stop service...");
        stopService(new Intent(this, MyService.class));
    }

    private void createProfile() {
        profile = new ProfileBean();
    }

    private void createSoundTask(SoundSettingBean soundBean) {
//        soundTask = new SoundTask(soundBean);
        profile.addTask(soundTask);
    }

    private void showPickTimeDialog() {
        //To change body of created methods use File | Settings | File Templates.
        Message msg = new Message();
        msg.what = SHOW_PICK_TIME_DIALOG;
        dateAndTimeHandler.sendMessage(msg);
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            createTimeEvent(hourOfDay, minute);
            saveProfile();
        }
    };

    private void saveProfile() {
        i("save profile");
        saveProfile();
    }

    private void createTimeEvent(int hourOfDay, int minute) {
        timeView.setText(hourOfDay + ":" + minute);
        timeEvent = new TimeEveryDayEvent(hourOfDay, minute);
        profile.addEvent(timeEvent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                if (timeEvent != null) {
                    mHour = timeEvent.getHour();
                    mMinute = timeEvent.getMinute();
                } else {
                    mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    mMinute = Calendar.getInstance().get(Calendar.MINUTE);
                }
                return new TimePickerDialog(this, timeSetListener, mHour, mMinute, true);
        }
        return null;
    }

    private void i(String message) {
        Log.i(getClass().getName(), message);
    }

    Handler dateAndTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PICK_TIME_DIALOG:
                    showDialog(TIME_DIALOG_ID);
                    break;
            }
        }
    };
}
