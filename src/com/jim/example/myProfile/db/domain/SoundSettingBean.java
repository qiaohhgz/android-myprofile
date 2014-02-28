package com.jim.example.myProfile.db.domain;

import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class SoundSettingBean {
    private int ring;

    public SoundSettingBean(int ring) {
        this.ring = ring;
    }

    public static SoundSettingBean getInstance(Bundle bundle) {
        return null;
    }

    public Bundle toBundle() {
        return null;
    }

    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

}
