package com.jim.example.myProfile;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.TableMapping;
import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.SoundTask;
import com.jim.example.myProfile.db.domain.TimeEveryDayEvent;
import com.jim.example.myProfile.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyService extends Service {
    private static String TAG = MyService.class.getName();
    private DBHelper helper = new DBHelper(this);
    private Handler objHandler = new Handler();
    private int intCounter = 0;
    private int delayed = 60 * 1000;

    private Runnable mTasks = new Runnable() {
        @Override
        public void run() {
            intCounter++;

            Log.d(TAG, "Counter:" + intCounter);
            check();

            objHandler.postDelayed(mTasks, delayed);
        }
    };

    private List<Profile> getProfileList() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor query = db.query(TableMapping.Profile.getTableName(), null, null, null, null, null, null);
        List<Profile> list = new ArrayList<Profile>();
        while (query.moveToNext()) {
            Profile p = new Profile();
            Log.d(TAG, "===== Profile Column Names =====");
            Log.d(TAG, StringUtils.join(query.getColumnNames(), " | "));
            int id = query.getInt(query.getColumnIndex("_id"));
            Log.d(TAG, "Id = " + id);
            p.setId(id);
            String name = query.getString(query.getColumnIndex("name"));
            p.setName(name);
            Log.d(TAG, "Name = " + name);
            boolean disable = query.getInt(query.getColumnIndex("disable")) == Profile.DISABLED;
            p.setDisable(disable);
            Log.d(TAG, "Disable = " + disable);
            list.add(p);
        }
        return list;
    }

    private List<TimeEveryDayEvent> getEvent(int profileID) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] values = new String[]{String.valueOf(profileID)};
        Cursor query = db.query(TableMapping.TimeEveryDayEvent.getTableName(), null, "profileID=?", values,
                null, null, null, null);
        List<TimeEveryDayEvent> list = new ArrayList<TimeEveryDayEvent>();
        while (query.moveToNext()) {
            TimeEveryDayEvent event = new TimeEveryDayEvent();
            Log.d(TAG, "===== Event Column Names =====");
            Log.d(TAG, StringUtils.join(query.getColumnNames(), " | "));
            int id = query.getInt(query.getColumnIndex("_id"));
            Log.d(TAG, "Id = " + id);
            event.setId(id);
            int hour = query.getInt(query.getColumnIndex("hour"));
            Log.d(TAG, "Hour = " + hour);
            event.setHour(hour);
            int minute = query.getInt(query.getColumnIndex("minute"));
            Log.d(TAG, "Minute = " + minute);
            event.setMinute(minute);

            list.add(event);
        }
        return list;
    }

    private List<SoundTask> getTask(int profileID) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] values = new String[]{String.valueOf(profileID)};
        Cursor query = db.query(TableMapping.SoundTask.getTableName(), null, "profileID=?", values, null, null, null);
        List<SoundTask> list = new ArrayList<SoundTask>();
        while (query.moveToNext()) {
            SoundTask task = new SoundTask();
            Log.d(TAG, "===== Task Column Names =====");
            Log.d(TAG, StringUtils.join(query.getColumnNames(), " | "));
            int ring = query.getInt(query.getColumnIndex("ring"));
            Log.d(TAG, "Ring = " + ring);
            task.setRing(ring);

            list.add(task);
        }
        return list;
    }


    public void check() {
        List<Profile> profiles = getProfileList();
        for (Profile profile : profiles) {
            if (profile.isDisable()) {
                continue;
            }
            List<TimeEveryDayEvent> events = getEvent(profile.getId());
            for (TimeEveryDayEvent event : events) {
                Log.d(TAG, "Event check.");
                if (event.check()) {
                    List<SoundTask> tasks = getTask(profile.getId());
                    for (SoundTask task : tasks) {
                        Log.d(TAG, "Running Task " + task.getId());
                        task.run(this);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objHandler.postDelayed(mTasks, 1000);
        Log.d(TAG, "create service successful.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
        objHandler.removeCallbacks(mTasks);
        Log.d(TAG, "destroy service successful.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, intent != null ? intent.getExtras().toString() : "intent is null");
        return null;
    }
}
