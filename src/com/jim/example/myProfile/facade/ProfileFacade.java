package com.jim.example.myProfile.facade;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.jim.example.myProfile.bean.ProfileBean;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.TableMapping;
import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.event.impl.TimeEveryDayEvent;
import com.jim.example.myProfile.db.domain.event.impl.TimeRangeEvent;
import com.jim.example.myProfile.db.domain.task.Task;
import com.jim.example.myProfile.db.domain.task.impl.SoundTask;
import com.jim.example.myProfile.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public class ProfileFacade implements IProfileFacade {
    private static final String TAG = ProfileFacade.class.getSimpleName();
    private DBHelper dbHelper;

    public ProfileFacade(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public ProfileBean getProfileBean(int id) {
        ProfileBean bean = new ProfileBean();
        bean.setProfile(getProfile(id));
        bean.getEvents().addAll(getTimeEveryDayEvent(id));
        bean.getTasks().addAll(getSoundTask(id));
        return bean;
    }

    @Override
    public Profile getProfile(int _id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor query = db.query(TableMapping.Profile.getTableName(), null, "_id=?", StringUtils.toArray(_id), null, null, null);
        Profile p = new Profile();
        if (query.moveToNext()) {
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
        }
        return p;
    }

    @Override
    public List<Task> getSoundTask(int profileID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] values = new String[]{String.valueOf(profileID)};
        Cursor query = db.query(TableMapping.SoundTask.getTableName(), null, "profileID=?", values, null, null, null);
        List<Task> list = new ArrayList<Task>();
        while (query.moveToNext()) {
            SoundTask task = new SoundTask();

            Log.d(TAG, "===== SoundTask Column Names =====");
            Log.d(TAG, StringUtils.join(query.getColumnNames(), " | "));

            int id = query.getInt(query.getColumnIndex("_id"));
            task.setId(id);

            int ring = query.getInt(query.getColumnIndex("ring"));
            task.setRing(ring);

            int alarm = query.getInt(query.getColumnIndex("alarm"));
            task.setAlarm(alarm);

            int music = query.getInt(query.getColumnIndex("music"));
            task.setMusic(music);

            int voiceCall = query.getInt(query.getColumnIndex("voiceCall"));
            task.setVoiceCall(voiceCall);

            list.add(task);
        }
        return list;
    }

    @Override
    public List<Profile> getProfileList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

    public List<Event> getTimeEveryDayEvent(int profileID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] values = new String[]{String.valueOf(profileID)};
        Cursor query = db.query(TableMapping.TimeEveryDayEvent.getTableName(), null, "profileID=?", values,
                null, null, null, null);
        List<Event> list = new ArrayList<Event>();
        while (query.moveToNext()) {
            TimeEveryDayEvent event = new TimeEveryDayEvent();

            Log.d(TAG, "===== TimeEveryDayEvent Column Names =====");
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

    @Override
    public List<Event> getTimeRangeEvent(int profileID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] values = new String[]{String.valueOf(profileID)};
        Cursor query = db.query(TableMapping.TimeRangeEvent.getTableName(), null, "profileID=?", values,
                null, null, null, null);
        List<Event> list = new ArrayList<Event>();
        while (query.moveToNext()) {
            TimeRangeEvent event = new TimeRangeEvent();

            Log.d(TAG, "===== TimeRangeEvent Column Names =====");
            Log.d(TAG, StringUtils.join(query.getColumnNames(), " | "));
            int id = query.getInt(query.getColumnIndex("_id"));
            Log.d(TAG, "Id = " + id);
            event.setId(id);
            String desc = query.getString(query.getColumnIndex("desc"));
            event.setDesc(desc);
            int fromHourOfDay = query.getInt(query.getColumnIndex("fromHourOfDay"));
            event.setFromHourOfDay(fromHourOfDay);
            int fromMinute = query.getInt(query.getColumnIndex("fromMinute"));
            event.setFromMinute(fromMinute);
            int toHourOfDay = query.getInt(query.getColumnIndex("toHourOfDay"));
            event.setToHourOfDay(toHourOfDay);
            int toMinute = query.getInt(query.getColumnIndex("toMinute"));
            event.setToMinute(toMinute);

            list.add(event);
        }
        return list;
    }

    @Override
    public void deleteProfile(int profileID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            String[] profileIDs = StringUtils.toArray(profileID);
            Log.d(TAG, "Delete profile ID = " + profileID);
            db.delete(TableMapping.Profile.getTableName(), "_id=?", profileIDs);
            Log.d(TAG, "Deleted profile successful.");
            db.delete(TableMapping.TimeEveryDayEvent.getTableName(), "profileID=?", profileIDs);
            db.delete(TableMapping.TimeRangeEvent.getTableName(), "profileID=?", profileIDs);
            Log.d(TAG, "Deleted event successful.");
            db.delete(TableMapping.SoundTask.getTableName(), "profileID=?", profileIDs);
            Log.d(TAG, "Deleted task successful.");
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void updateProfile(Profile profile) {
        //TODO Writing updateProfile method
    }

    @Override
    public void updateProfileBean(ProfileBean profileBean) {
        //TODO Writing updateProfileBean method
    }
}
