package com.jim.example.myProfile.util;

import android.media.AudioManager;
import android.util.Log;
import android.widget.SeekBar;

/**
 * JimQiao
 * 14-3-13 上午10:22
 */
public class AudioManagerHelper {
    public void setStreamVolume(AudioManager mAudioManager, SeekBar seekBar, int streamType) {
        setStreamVolume(mAudioManager, seekBar, streamType, 0);
    }

    public void setStreamVolume(AudioManager mAudioManager, SeekBar seekBar, int streamType, int addMax) {
        int max = mAudioManager.getStreamMaxVolume(streamType) + addMax;
        int current = mAudioManager.getStreamVolume(streamType) + 1;
        seekBar.setMax(max);
        seekBar.setProgress(current);
        Log.d("AudioManagerHelper", "Current: " + current + " Max:" + max);
    }
}
