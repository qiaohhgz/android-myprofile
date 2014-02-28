package com.jim.example.myProfile.bean;

import com.jim.example.myProfile.db.domain.Event;
import com.jim.example.myProfile.db.domain.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 2/19/14
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileBean {
    private boolean disable;
    private List<Event> eventList;
    private List<Task> taskList;

    public ProfileBean() {
        this.eventList = new ArrayList<Event>();
        this.taskList = new ArrayList<Task>();
    }

    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    public void removeEvent(int id) {

    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public void removeTask(int id) {

    }

    public Event getEvent(int id) {
        for (Event event : eventList) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    public Task getTask(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void checkAll() {
        for (Event event : eventList) {
            for (Task task : taskList) {
                if (event.check()) {
                    task.run();
                }
            }
        }
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
