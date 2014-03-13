package com.jim.example.myProfile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.jim.example.myProfile.adapter.ProfileAdapter;
import com.jim.example.myProfile.bean.ProfileBean;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.TableMapping;
import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.event.impl.TimeEveryDayEvent;
import com.jim.example.myProfile.db.domain.task.Task;
import com.jim.example.myProfile.db.domain.task.impl.SoundTask;
import com.jim.example.myProfile.facade.ILog;
import com.jim.example.myProfile.facade.IProfileFacade;
import com.jim.example.myProfile.facade.ProfileFacade;
import com.jim.example.myProfile.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListHistoryActivity extends ListActivity {
    private static final String TAG = ListHistoryActivity.class.getSimpleName();
    private DBHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.helper = new DBHelper(this);

        initializeViews();

        displayData();
    }

    private void displayData() {

        String[] from = {"level", "tag", "msg"};
        int[] to = {R.id.item_history_level, R.id.item_history_tag, R.id.item_history_msg};
        Cursor cursor = helper.getReadableDatabase().rawQuery("select * from ?", StringUtils.toArray(TableMapping.History.getTableName()));

        Function levelFormat = new Function() {
            @Override
            public void run(TextView v, String text) {
                switch (Integer.valueOf(text)) {
                    case ILog.ASSERT:
                        v.setText("ASSERT");
                        break;
                    case ILog.DEBUG:
                        v.setText("DEBUG");
                        break;
                    case ILog.ERROR:
                        v.setText("ERROR");
                        break;
                    case ILog.INFO:
                        v.setText("INFO");
                        break;
                    case ILog.WARN:
                        v.setText("WARN");
                        break;
                }
            }
        };
        Function simpleFunction = new SimpleFunction();
        Function[] fs = {levelFormat, simpleFunction, simpleFunction};
        ProfileAdapter profileAdapter = new ProfileAdapter(this, R.layout.history_list_item, cursor, from, to, fs);

        getListView().setAdapter(profileAdapter);
    }


    private void initializeViews() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
