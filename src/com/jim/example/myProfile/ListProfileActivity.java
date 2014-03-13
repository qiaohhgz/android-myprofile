package com.jim.example.myProfile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import com.jim.example.myProfile.bean.ProfileBean;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.event.impl.TimeEveryDayEvent;
import com.jim.example.myProfile.db.domain.task.Task;
import com.jim.example.myProfile.db.domain.task.impl.SoundTask;
import com.jim.example.myProfile.facade.IProfileFacade;
import com.jim.example.myProfile.facade.ProfileFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListProfileActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = ListProfileActivity.class.getSimpleName();
    private DBHelper helper;
    private IProfileFacade profileFacade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.helper = new DBHelper(this);
        this.profileFacade = new ProfileFacade(this.helper);

        initializeViews();

        displayData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        List<Profile> profileList = profileFacade.getProfileList();

        String[] from = {"name", "description", "createDate", "disable"};
        int[] to = {R.id.item_name, R.id.item_desc, R.id.item_create_date, R.id.item_disable};
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < profileList.size(); i++) {
            Profile profile = profileList.get(i);
            ProfileBean bean = profileFacade.getProfileBean(profile.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(from[0], profile.getName());
            StringBuffer desc = new StringBuffer();
            desc.append(getResources().getString(R.string.list_task_title));
            desc.append("\n");
            for (Task task : bean.getTasks()) {
                if (task instanceof SoundTask) {
                    SoundTask soundTask = (SoundTask) task;
                    desc.append(String.format(getResources().getString(R.string.sound_task_summary_format),
                            soundTask.getRing(), soundTask.getAlarm(),
                            soundTask.getMusic(), soundTask.getVoiceCall()));
                    desc.append("\n");
                }
            }
            desc.append(getResources().getString(R.string.list_event_title));
            desc.append("\n");
            for (Event event : bean.getEvents()) {
                if (event instanceof TimeEveryDayEvent) {
                    TimeEveryDayEvent dayEvent = (TimeEveryDayEvent) event;
                    desc.append(String.format(getResources().getString(R.string.time_event_summary_format),
                            dayEvent.getHour(), dayEvent.getMinute()));
                    desc.append("\n");
                }
            }
            desc.append(profile.getCreateDate());
            map.put(from[1], desc.toString());
            map.put(from[2], profile.getCreateDate());
            String disableValue = profile.isDisable() ? getResources().getString(R.string.disable) : getResources().getString(R.string.enable);
            map.put(from[3], disableValue);

            data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.profile_list_item, from, to);
        getListView().setAdapter(simpleAdapter);
    }


    private void initializeViews() {
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long id) {
        Log.d(TAG, "item click");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                int profileID = (int) id;
                Log.d(TAG, "Delete profileID = " + profileID);
                profileFacade.deleteProfile(profileID);
                Log.d(TAG, "Display data.");
                displayData();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // no nothing
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
