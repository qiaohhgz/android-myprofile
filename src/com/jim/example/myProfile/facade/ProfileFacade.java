package com.jim.example.myProfile.facade;

import android.database.Cursor;
import com.jim.example.myProfile.bean.ProfileBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public interface ProfileFacade {
    List<ProfileBean> getAll();

    ProfileBean update(ProfileBean profileBean);

    ProfileBean add(ProfileBean bean);

}
