package com.jim.example.myProfile;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.jim.example.myProfile.db.dao.*;
import com.jim.example.myProfile.util.AudioManagerHelper;
import com.jim.example.myProfile.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddProfileActivity extends Activity implements View.OnClickListener {
    private static String TAG;
    private DBHelper dbHelper = new DBHelper(this);
    private AudioManagerHelper audioManagerHelper = new AudioManagerHelper();

    private EditText nameET, descET;
    private ToggleButton disableTB;

    private TextView lblPickTime;
    private Button btnPickTime;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private int hourOfDay, minute;

    private SeekBar seekRing;
    private SeekBar seekAlarm;
    private SeekBar seekMusic;
    private SeekBar seekVoiceCall;

    private Button addBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_add);

        initializeViews();

    }

    private void initializeViews() {
        // Profile info
        nameET = (EditText) findViewById(R.id.add_name);
        descET = (EditText) findViewById(R.id.add_desc);
        disableTB = (ToggleButton) findViewById(R.id.btn_disable);

        // Event
        lblPickTime = (TextView) findViewById(R.id.lbl_pick_time);
        btnPickTime = (Button) findViewById(R.id.btn_pick_time);
        btnPickTime.setOnClickListener(this);
        initTimePickerDialog();

        // Task
        this.seekRing = (SeekBar) findViewById(R.id.seek_task_ring);
        this.seekAlarm = (SeekBar) findViewById(R.id.seek_task_alarm);
        this.seekMusic = (SeekBar) findViewById(R.id.seek_task_music);
        this.seekVoiceCall = (SeekBar) findViewById(R.id.seek_task_voice_call);
        loadRingData();

        // Save button
        addBtn = (Button) findViewById(R.id.btn_save);
        addBtn.setOnClickListener(this);
    }

    private void loadRingData() {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        audioManagerHelper.setStreamVolume(mAudioManager, seekRing, AudioManager.STREAM_RING, 1);
        audioManagerHelper.setStreamVolume(mAudioManager, seekAlarm, AudioManager.STREAM_ALARM);
        audioManagerHelper.setStreamVolume(mAudioManager, seekMusic, AudioManager.STREAM_MUSIC);
        audioManagerHelper.setStreamVolume(mAudioManager, seekVoiceCall, AudioManager.STREAM_VOICE_CALL);
    }


    private void initTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourView = true;

        this.timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d(TAG, "Update time hourOfDay = " + hourOfDay + " minute = " + minute);
                updateTime(hourOfDay, minute);
            }
        };
        this.timePickerDialog = new TimePickerDialog(this, timeSetListener, hourOfDay, minute, is24HourView);

    }

    private void updateTime(int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.lblPickTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveProfile();
                break;
            case R.id.btn_pick_time:
                showPickTimeDialog();
                break;
        }
    }

    private void showPickTimeDialog() {
        this.timePickerDialog.show();
    }

    private void saveProfile() {
        // get values
        Log.d(TAG, "======== Get values ========");
        String name = nameET.getText().toString();
        Log.d(TAG, "Name is " + name);
        String desc = descET.getText().toString();
        Log.d(TAG, "Desc is " + desc);
        boolean disable = disableTB.isChecked();
        Log.d(TAG, "Disable is " + disable);

        // check values
        if (name == null || name.length() == 0) {
            String msg = "Name is null.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg);
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            // save profile
            long profileID = db.insert(TableMapping.Profile.getTableName(), null, buildProfile(name, desc, disable));
            Log.d(TAG, "Save profile successful. ProfileID = " + profileID);

            // save task
            ContentValues soundTaskValues = buildSoundTask((int) profileID, seekRing.getProgress(),
                    seekAlarm.getProgress(), seekMusic.getProgress(), seekVoiceCall.getProgress());
            db.insert(TableMapping.SoundTask.getTableName(), null, soundTaskValues);
            Log.d(TAG, "Save task successful.");

            // save event
            db.insert(TableMapping.TimeEveryDayEvent.getTableName(), null, buildTimeEvent((int) profileID, hourOfDay, minute));
            Log.d(TAG, "Save event successful.");

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } finally {
            db.endTransaction();
        }

        // start activity
        Log.d(TAG, "Go to List activity.");
        Intent intent = new Intent(this, ListProfileActivity.class);
        startActivity(intent);
    }

    private ContentValues buildTimeEvent(int profileID, int hourOfDay, int minute) {
        ContentValues values = new ContentValues();
        values.put("hour", hourOfDay);
        values.put("minute", minute);
        values.put("profileID", profileID);
        return values;
    }

    private ContentValues buildSoundTask(int profileID, int ring, int alarm, int music, int voiceCall) {
        ContentValues values = new ContentValues();
        values.put("profileID", profileID);
        values.put("ring", ring);
        values.put("alarm", alarm);
        values.put("music", music);
        values.put("voiceCall", voiceCall);
        return values;
    }

    private ContentValues buildProfile(String name, String desc, boolean disable) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_FORMAT_STRING);
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", desc);
        values.put("createDate", format.format(new Date()));
        values.put("disable", disable);
        return values;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        Log.i(TAG, "dbHelper closing...");
        dbHelper.close();
    }
}
