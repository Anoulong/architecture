package com.prototype.architecture.mvvm.dependency;


import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.ui.MainActivity;
import com.prototype.architecture.mvvm.ui.base.BaseActivity;
import com.prototype.architecture.mvvm.ui.base.BaseFragment;
import com.prototype.architecture.mvvm.ui.news.NewsFragment;
import com.prototype.architecture.mvvm.ui.settings.SettingsActivity;
import com.prototype.architecture.mvvm.ui.shelf.ShelfFragment;
import com.prototype.architecture.mvvm.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

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
 * Created by Anou Chanthavong on 2017-12-01.
 ******************************************************************************/
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    // Injectable Application
    void inject(RcaApplication target);

    //region Injectable Activities
    void inject(BaseActivity target);
    void inject(MainActivity target);
    void inject(SplashActivity target);
    void inject(SettingsActivity target);
    //endregion


    //region Injectable Fragments
    void inject(BaseFragment target);
    void inject(ShelfFragment target);
    void inject(NewsFragment target);
    //endregion

}
