package com.prototype.architecture.mvvm.coordinator;

import android.content.Intent;
import android.util.Log;

import com.mvvm.core.local.module.Module;
import com.prototype.architecture.mvvm.ui.MainActivity;
import com.prototype.architecture.mvvm.ui.base.BaseActivity;
import com.prototype.architecture.mvvm.ui.drawer.DrawerAdapter;
import com.prototype.architecture.mvvm.ui.news.NewsFragment;
import com.prototype.architecture.mvvm.ui.settings.SettingsActivity;
import com.prototype.architecture.mvvm.ui.splash.SplashActivity;


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
 * Created by Anou Chanthavong on 2017-12-15.
 ******************************************************************************/
public class Coordinator {
    private static final String TAG = Coordinator.class.getSimpleName();

    private String latestSelectedModuleEid = "";

    public Coordinator() {
    }

    public String getLatestSelectedModuleEid() {
        Log.d(TAG, "getLatestSelectedModuleEid: " + latestSelectedModuleEid);
        return latestSelectedModuleEid;
    }

    public void showMainScreen(SplashActivity splashActivity) {
        Intent intent = MainActivity.intent(splashActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        splashActivity.startActivity(intent);
        splashActivity.finish();
    }

    public void didDrawerItemSelected(BaseActivity baseActivity, DrawerAdapter.DrawerItem item) {
        Log.d(TAG, "didDrawerItemSelected: " + item.getItemType().name());
        switch (item.getItemType()) {
            case module:
                latestSelectedModuleEid = item.getModule().getEid();
                switch (item.getModule().getType()) {
                    case Module.HOME:
//                        Navigator.showScreen(parentFrag, new HomeFragmentForWildFire());
                        break;

                    case Module.QUIZ:
//                        AppManager.menuItem = MenuItems.QUIZ;
//                        Navigator.showScreen(parentFrag, new ListsFragment());
                        break;

                    case Module.RESOURCES:
                    case Module.CHECKLIST:
                    case Module.VIDEOS:
                    case Module.PDF:
                    case Module.FAQ:
//                        AppManager.menuItem = MenuItems.CATEGORY;
//                        Navigator.showScreen(parentFrag, new ListsFragment());
                        break;

                    case Module.NEWS:
                        baseActivity.replaceFragment(NewsFragment.newInstance(), true);
//                        Navigator.showScreen(parentFrag, new HomeFragment());
                        break;

                    case Module.LIBRARY:
//                        Navigator.showScreen(parentFrag, new ShelfFragment());// comment this line to get reader temporarily
                        // Helper.launchReader(); // Uncomment this line to get reader temporarily
                        break;

                    case Module.ABOUT:
                    case Module.TEXT_TYPE:
//                        Bundle bundle = new Bundle();
//                        String item = "";
//                        if (AppManager.modules.get(position).getTitle().toLowerCase().contains("about"))
//                            item = AppManager.app.getAbout();
//
//                        else if (AppManager.modules.get(position).getTitle().toLowerCase().contains("welcome"))
//                            item = AppManager.app.getWelcomeMsg();
//
//                        bundle.putString(AppManager.OBJECT_KEY, item);
//                        Navigator.showScreen(parentFrag, new TextModuleFragment(), bundle);
                        break;
                }


                break;
            case settings:
                baseActivity.startActivity(SettingsActivity.intent(baseActivity));
                baseActivity.finish();
                break;
        }
    }

    public void didBackButtonTapped(SettingsActivity settingsActivity) {
        Intent intent = MainActivity.intent(settingsActivity);
        settingsActivity.startActivity(intent);
        settingsActivity.finish();
    }
}
