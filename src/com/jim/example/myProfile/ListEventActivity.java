package com.jim.example.myProfile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.TableMapping;

public class ListEventActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private static String TAG;
    private DBHelper helper = new DBHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TAG = getClass().getName();

        super.onCreate(savedInstanceState);

        initializeViews();

        displayData();
    }

    private void displayData() {
        Cursor query = helper.getReadableDatabase().query(TableMapping.timeEveryDayEvent.getTableName(), null, null, null, null, null, null);
        String[] from = {"profileID", "hour", "minute"};
        int[] to = {R.id.item_event_profileID, R.id.item_event_hour, R.id.item_event_minute};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.event_list_item, query, from, to);

        getListView().setAdapter(adapter);
    }


    private void initializeViews() {
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long id) {
        Log.d(TAG, "item click");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("真的要删除该记录吗？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                int eventID = helper.getWritableDatabase().delete(TableMapping.timeEveryDayEvent.getTableName(), "_id=?", new String[]{String.valueOf(id)});
                Log.d(TAG, "Delete eventID = " + eventID);
                displayData();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
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
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        helper.close();
    }
}
