package com.jim.example.myProfile.facade;

import com.jim.example.myProfile.bean.ProfileBean;
import com.jim.example.myProfile.db.domain.*;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.task.Task;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public interface IProfileFacade {
    ProfileBean getProfileBean(int id);

    Profile getProfile(int id);

    List<Task> getSoundTask(int profileID);

    List<Event> getTimeEveryDayEvent(int profileID);

    List<Event> getTimeRangeEvent(int profileID);

    List<Profile> getProfileList();

    void deleteProfile(int profileID);

    void updateProfile(Profile profile);

    void updateProfileBean(ProfileBean profileBean);
}
