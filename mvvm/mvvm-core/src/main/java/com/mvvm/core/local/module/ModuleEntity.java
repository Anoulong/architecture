package com.mvvm.core.local.module;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2018-01-29.
 ******************************************************************************/
@Entity
public class ModuleEntity {

    final public static String TEXT_TYPE = "text";
    final public static String ABOUT = "about-us";
    final public static String HOME = "home";
    final public static String QUIZ = "scored-assessment";
    final public static String NEWS = "news";
    final public static String RESOURCES = "resources";
    final public static String LIBRARY = "library";
    final public static String CHECKLIST = "checklists";
    final public static String VIDEOS = "videos";
    final public static String PDF = "pdfs";
    final public static String FAQ = "faq";

    @PrimaryKey
    @NonNull
    String _id;
    String appEid;
    String eid;
    String title;
    String description;
    String type;
    boolean active;
    String createdAt;
    String updatedAt;


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getAppEid() {
        return appEid;
    }

    public void setAppEid(String appEid) {
        this.appEid = appEid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}