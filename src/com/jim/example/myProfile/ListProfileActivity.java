package com.jim.example.myProfile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.dao.ProfileDao;
import com.jim.example.myProfile.db.dao.TableMapping;
import com.jim.example.myProfile.db.domain.Profile;

public class ListProfileActivity extends ListActivity implements AdapterView.OnItemClickListener {
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
        Cursor query = helper.getReadableDatabase().query(TableMapping.Profile.getTableName(), null, null, null, null, null, null);
        String[] from = {"name", "description", "createDate", "disable"};
        int[] to = {R.id.item_name, R.id.item_desc, R.id.item_create_date, R.id.item_disable};
        SimpleFunction sFun = new SimpleFunction();
        Function disableFun = new Function() {
            @Override
            public void run(TextView v, String text) {
                boolean isDisable = Integer.valueOf(text) == Profile.DISABLED;
                if (isDisable) {
                    v.setText(R.string.disable);
                } else {
                    v.setText(R.string.enable);
                }
            }
        };
        Function[] funs = new Function[]{sFun, sFun, sFun, disableFun};
        getListView().setAdapter(new ProfileAdapter(this, R.layout.profile_list_item, query, from, to, funs));
    }


    private void initializeViews() {
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long id) {
        Log.d(TAG, "item click");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                int profileID = (int) id;
                Log.d(TAG, "Delete profileID = " + profileID);
                deleteProfile(profileID);
                Log.d(TAG, "Display data.");
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

    private void deleteProfile(int profileID) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            String[] values = {String.valueOf(profileID)};
            Log.d(TAG, "Delete profile ID = " + profileID);
            db.delete(TableMapping.Profile.getTableName(), "_id=?", values);
            Log.d(TAG, "Deleted profile successful.");
            db.delete(TableMapping.TimeEveryDayEvent.getTableName(), "profileID=?", values);
            Log.d(TAG, "Deleted event successful.");
            db.delete(TableMapping.SoundTask.getTableName(), "profileID=?", values);
            Log.d(TAG, "Deleted task successful.");
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        helper.close();
    }

    public static class ProfileAdapter extends SimpleCursorAdapter {
        private Function[] functions;
        private int index;

        public ProfileAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }

        public ProfileAdapter(Context context, int layout, Cursor c, String[] from, int[] to, Function[] functions) {
            super(context, layout, c, from, to);
            this.functions = functions;
        }

        @Override
        public void setViewText(TextView v, String text) {
            if (functions != null) {
                Function fun = functions[index];
                fun.run(v, text);
                boolean isLast = index == (functions.length - 1);
                if (isLast) {
                    index = 0;
                } else {
                    index++;
                }
            } else {
                super.setViewText(v, text);
            }
        }
    }
}
