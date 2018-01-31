package com.mvvm.core.local.module;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

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

    public static final String TEXT_TYPE = "text";
    public static final String ABOUT = "about-us";
    public static final String HOME = "home";
    public static final String QUIZ = "scored-assessment";
    public static final String NEWS = "news";
    public static final String RESOURCES = "resources";
    public static final String LIBRARY = "library";
    public static final String CHECKLIST = "checklists";
    public static final String VIDEOS = "videos";
    public static final String PDF = "pdfs";
    public static final String FAQ = "faq";

    @PrimaryKey
    @NonNull
    private String _id;
    private String appEid;
    private String eid;
    private String title;
    private String slug;
    private String type;
    private String description;
    private boolean active;
    private String createdAt;
    private String updatedAt;


    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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