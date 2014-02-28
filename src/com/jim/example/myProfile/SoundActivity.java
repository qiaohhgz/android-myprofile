package com.jim.example.myProfile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jim.example.myProfile.db.domain.SoundSettingBean;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoundActivity extends Activity implements View.OnClickListener{
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        initializeViews();

        SoundSettingBean soundBean = SoundSettingBean.getInstance(getIntent().getExtras());

        setSoundVolume(soundBean);


    }

    private void initializeViews() {
        //To change body of created methods use File | Settings | File Templates.

    }

    private void setSoundVolume(SoundSettingBean soundBean) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private SoundSettingBean getSoundByCurrentView() {
        return null;
    }

    @Override
    public void onClick(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
