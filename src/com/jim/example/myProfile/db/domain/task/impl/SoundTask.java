package com.jim.example.myProfile.db.domain.task.impl;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import com.jim.example.myProfile.db.domain.task.Task;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoundTask implements Task {
    private static final String TAG = SoundTask.class.getName();
    private int id;
    private int profileID;
    private int ring;
    private int alarm;
    private int music;
    private int voiceCall;

    public SoundTask() {

    }

    public void execute(Context context) {
        Log.d(TAG, "Start sound task.");
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, ring, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarm, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, music, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, voiceCall, 0);

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

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getVoiceCall() {
        return voiceCall;
    }

    public void setVoiceCall(int voiceCall) {
        this.voiceCall = voiceCall;
    }
}
