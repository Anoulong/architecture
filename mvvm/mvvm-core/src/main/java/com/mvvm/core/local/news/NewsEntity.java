package com.mvvm.core.local.news;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by laf on 2017-05-05.
 */
@Entity
public class NewsEntity implements Serializable{

    @PrimaryKey
    @NonNull
    private String _id;
    private String slug;
    private String eid;
    @Embedded
    private Title title;
    @Embedded
    private Photo photo;

    @NonNull
    public String getId() {
        return _id;
    }

    public void setId(@NonNull String _id) {
        this._id = _id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    //region Embedded Classes
    @Entity
    public static class Title {
        @ColumnInfo(name = "title_en")
        private String en;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    @Entity
    public static class Photo {
        @ColumnInfo(name = "photo_en")
        private String en;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }
    //endregion
}
