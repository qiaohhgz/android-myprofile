package com.jim.example.myProfile.db.domain;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoundTask implements Task {
    private int id;
    private int ring;
    private int profileID;

    public SoundTask() {

    }

    @Override
    public void run() {
        Log.d(getClass().getName(), "run");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }
}
