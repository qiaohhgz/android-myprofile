package com.jim.example.myProfile;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.jim.example.myProfile.db.dao.DBHelper;
import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.task.Task;
import com.jim.example.myProfile.facade.IProfileFacade;
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
    private static String TAG = MyService.class.getSimpleName();
    private DBHelper helper = new DBHelper(this);
    private Handler objHandler = new Handler();
    private IProfileFacade profileFacade = new ProfileFacade(helper);
    private int intCounter = 0;
    private int delayed = 10 * 1000;

    public MyService() {
        super();
        Log.d(TAG, "Run MyService Constructor.");
    }

    private Runnable mTasks = new Runnable() {
        @Override
        public void run() {
            intCounter++;

            Log.d(TAG, "Counter:" + intCounter);
            check();

            objHandler.postDelayed(mTasks, delayed);
        }
    };

    public void check() {
        List<Profile> profiles = profileFacade.getProfileList();
        for (Profile profile : profiles) {
            if (profile.isDisable()) {
                continue;
            }
            List<Event> events = profileFacade.getTimeEveryDayEvent(profile.getId());
            for (Event event : events) {
                if (event.check(this)) {
                    List<Task> tasks = profileFacade.getSoundTask(profile.getId());
                    for (Task task : tasks) {
                        task.execute(this);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        objHandler.postDelayed(mTasks, 1000);
        Log.d(TAG, "create service successful.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
        objHandler.removeCallbacks(mTasks);
        Log.d(TAG, "destroy service successful.");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, intent != null ? intent.getExtras().toString() : "intent is null");
        return null;
    }
}
