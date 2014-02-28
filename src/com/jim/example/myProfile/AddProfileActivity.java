package com.jim.example.myProfile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.jim.example.myProfile.db.dao.ProfileDao;
import com.jim.example.myProfile.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProfileActivity extends Activity implements View.OnClickListener {
    private static String TAG;
    private ProfileDao profileDao = new ProfileDao(this);


    private EditText nameET, descET;
    private ToggleButton disableTB;
    private Button addBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = getClass().getName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_add);

        initializeViews();
    }

    private void initializeViews() {
        nameET = (EditText) findViewById(R.id.add_name);
        descET = (EditText) findViewById(R.id.add_desc);
        disableTB = (ToggleButton) findViewById(R.id.add_disable_Btn);
        addBtn = (Button) findViewById(R.id.add_save_Btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
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
            Log.d(TAG, "Name is null.");
            return;
        }

        // save profile
        profileDao.insert(buildProfile(name, desc, disable));
        Log.d(TAG, "Save successful.");

        // start activity
        Log.d(TAG, "Go to List activity.");
        Intent intent = new Intent(this, ListProfileActivity.class);
        startActivity(intent);
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
}
