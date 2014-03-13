package com.jim.example.myProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    /**
     * Called when the activity is first created.
     */
    private static String TAG;
    private static boolean isEnabledService = true;

    private Button gotoAddActivityBtn;
    private Button gotoListActivityBtn;
    private Button gotoEventListActivity;
    private Button gotoTaskListActivity;
    private CheckBox usingCbx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();
    }

    private void initializeViews() {
        gotoAddActivityBtn = (Button) findViewById(R.id.gotoAddProfileBtn);
        gotoAddActivityBtn.setOnClickListener(this);

        gotoListActivityBtn = (Button) findViewById(R.id.gotoProfileList);
        gotoListActivityBtn.setOnClickListener(this);

        gotoEventListActivity = (Button) findViewById(R.id.gotoEventList);
        gotoEventListActivity.setOnClickListener(this);

        gotoTaskListActivity = (Button) findViewById(R.id.gotoTaskList);
        gotoTaskListActivity.setOnClickListener(this);

        usingCbx = (CheckBox) findViewById(R.id.usingCbx);
        usingCbx.setOnCheckedChangeListener(this);
        usingCbx.setChecked(isEnabledService);
        changeService(isEnabledService);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.usingCbx:
                changeService(b);
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
        String msg = "start service...";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        Log.i(TAG, msg);
        Intent i = new Intent(this, MyService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
    }

    private void stopService() {
        String msg = "stop service...";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        Log.i(TAG, msg);
        stopService(new Intent(this, MyService.class));
    }

    private void changeService(boolean enabled) {
        if (enabled == isEnabledService) {
            return;
        }
        Toast.makeText(this, "Change service " + enabled, Toast.LENGTH_SHORT);
        isEnabledService = enabled;
        if (enabled) {
            startService();
        } else {
            stopService();
        }
    }

}
