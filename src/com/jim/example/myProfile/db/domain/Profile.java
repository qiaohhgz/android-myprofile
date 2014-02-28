package com.jim.example.myProfile.db.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jim_qiao
 * Date: 14-2-24
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class Profile {
    private int id;
    private String name;
    private String description;
    private Date createDate;
    private boolean disable;

    public Profile() {
    }

    public Profile(String name) {
        this.name = name;
    }

    public Profile(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
