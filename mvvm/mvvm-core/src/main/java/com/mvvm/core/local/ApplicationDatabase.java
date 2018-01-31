package com.mvvm.core.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mvvm.core.local.module.ModuleDao;
import com.mvvm.core.local.module.Module;
import com.mvvm.core.local.news.NewsDao;
import com.mvvm.core.local.news.News;

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
@Database(entities = {Module.class, News.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "qs-rca-db";

    public abstract ModuleDao moduleDao();
    public abstract NewsDao newsDao();
}
