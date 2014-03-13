package com.jim.example.myProfile.bean;

import com.jim.example.myProfile.db.domain.Profile;
import com.jim.example.myProfile.db.domain.event.Event;
import com.jim.example.myProfile.db.domain.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * JimQiao
 * 14-3-13 上午11:14
 */
public class ProfileBean {
    private Profile profile;
    private List<Task> tasks;
    private List<Event> events;

    public ProfileBean() {
        this.tasks = new ArrayList<Task>();
        this.events = new ArrayList<Event>();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
