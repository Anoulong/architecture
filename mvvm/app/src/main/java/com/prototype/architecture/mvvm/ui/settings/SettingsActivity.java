package com.prototype.architecture.mvvm.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.coordinator.Coordinator;
import com.prototype.architecture.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

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
 * Created by Anou Chanthavong on 2017-12-16.
 ******************************************************************************/
public class SettingsActivity extends BaseActivity {

    @Inject
    Coordinator coordinator;

    public static Intent intent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RcaApplication.getApplicationComponent(this).inject(this);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        coordinator.didBackButtonTapped(SettingsActivity.this);
    }
}
