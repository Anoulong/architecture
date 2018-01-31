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
    @Embedded
    private PhotoCredit photo_credit;
    @Embedded
    private Caption caption;
    @Embedded
    private Body body;
    private String author_eid;
    @Embedded
    private Fields fields;

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
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

    public PhotoCredit getPhoto_credit() {
        return photo_credit;
    }

    public void setPhoto_credit(PhotoCredit photo_credit) {
        this.photo_credit = photo_credit;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getAuthor_eid() {
        return author_eid;
    }

    public void setAuthor_eid(String author_eid) {
        this.author_eid = author_eid;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
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

    @Entity
    public static class PhotoCredit {
        @ColumnInfo(name = "photo_credit")
        private String en;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    @Entity
    public static class Caption {
        @ColumnInfo(name = "caption_en")
        private String en;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    @Entity
    public static class Body {
        @ColumnInfo(name = "body_en")
        private String en;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    @Entity
    public static class Fields {
        @ColumnInfo(name = "fields_tag")
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    //endregion
}
