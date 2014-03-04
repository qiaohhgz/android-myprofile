package com.jim.example.myProfile.db.domain;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoundTask {
    private static final String TAG = SoundTask.class.getName();
    private int id;
    private int ring;
    private int profileID;

    public SoundTask() {

    }

    public void run(Context context) {
        Log.d(TAG, "Start sound task.");
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, ring, 0);
        Log.d(TAG, "End sound task.");
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

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }
}
