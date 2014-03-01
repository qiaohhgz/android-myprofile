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

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-3-2
 * Time: 上午12:06
 * To change this template use File | Settings | File Templates.
 */
public class ListTaskActivity extends ListActivity implements AdapterView.OnItemClickListener {
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
        Cursor query = helper.getReadableDatabase().query(TableMapping.SoundTask.getTableName(), null, null, null, null, null, null);
        String[] from = {"profileID", "ring"};
        int[] to = {R.id.item_task_profileID, R.id.item_task_ring};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.task_list_item, query, from, to);

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
                int taskID = helper.getWritableDatabase().delete(TableMapping.SoundTask.getTableName(), "_id=?", new String[]{String.valueOf(id)});
                Log.d(TAG, "Delete taskID = " + taskID);
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
