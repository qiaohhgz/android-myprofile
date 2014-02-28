package com.jim.example.myProfile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import com.jim.example.myProfile.db.dao.ProfileDao;

public class ListProfileActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private ProfileDao profileDao = new ProfileDao(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeViews();

        displayData();
    }

    private void displayData() {
        profileDao.query();
        String[] from = {"name", "description", "createDate", "disable"};
        int[] to = {R.id.item_name, R.id.item_desc, R.id.item_create_date, R.id.item_disable_btn};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.profile_list_item, profileDao.query(), from, to);

        getListView().setAdapter(adapter);
    }



    private void initializeViews() {
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("真的要删除该记录吗？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                profileDao.del((int) id);
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
        profileDao.close();
    }
}
