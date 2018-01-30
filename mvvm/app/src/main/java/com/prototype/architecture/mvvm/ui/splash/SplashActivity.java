package com.prototype.architecture.mvvm.ui.splash;

import android.os.Bundle;


import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.coordinator.Coordinator;
import com.prototype.architecture.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    Coordinator coordinator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RcaApplication.getApplicationComponent(this).inject(this);

        coordinator.showMainScreen(SplashActivity.this );

    }
}
