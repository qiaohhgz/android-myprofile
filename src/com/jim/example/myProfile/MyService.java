package com.jim.example.myProfile;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.jim.example.myProfile.bean.ProfileBean;
import com.jim.example.myProfile.facade.ProfileFacade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyService extends Service {
    private Handler objHandler = new Handler();
    private ProfileFacade profileFacade;
    private int intCounter = 0;
    private int delayed = 1000;

    private Runnable mTasks = new Runnable() {
        @Override
        public void run() {
            intCounter++;

            Log.d(getClass().getName(), "Counter:" + intCounter);
            List<ProfileBean> profiles = profileFacade.getAll();
            Log.d(getClass().getName(), "Profiles.size = " + profiles.size());
            for (ProfileBean profile : profiles) {
                profile.checkAll();
            }

            objHandler.postDelayed(mTasks, delayed);
        }
    };

    public void check() {
        List<ProfileBean> profiles = profileFacade.getAll();
        Log.d(getClass().getName(), "Profiles.size = " + profiles.size());
        for (ProfileBean profile : profiles) {
            profile.checkAll();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
        objHandler.postDelayed(mTasks, 1000);
        i("create service successful.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        objHandler.removeCallbacks(mTasks);
        i("destroy service successful.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        i(intent != null ? intent.getExtras().toString() : "intent is null");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void i(String message) {
        Log.i(getClass().getName(), message);
    }
}
