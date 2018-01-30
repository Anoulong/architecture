package com.prototype.architecture.mvvm.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.mvvm.core.common.utils.ThreadUtils;
import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.service.NetworkStateBroadcastReceiver;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private CompositeDisposable activitySubscriptions;
    private ProgressDialog progressDialog;

    @Inject
    NetworkStateBroadcastReceiver networkStateBroadcastReceiver;

    public BaseActivity() {
    }

    //region Overriding Methods to handle android lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RcaApplication.getApplicationComponent(this).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        setupObservables();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStateBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearObservables();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearObservables();
    }
    //endregion

    //region Utility Methods to manage Rx Observable in Activities
    /**
     * Handle Rx Java subscription
     * @param subscription
     */
    protected final void addDisposable(Disposable subscription) {
        activitySubscriptions.add(subscription);
    }

    protected final void clearObservables() {
        if (activitySubscriptions != null && !activitySubscriptions.isDisposed()) {
            activitySubscriptions.dispose();
        }
    }

    /**
     * Make sure to call super.setupObservables() when overriding this.
     */
    protected void setupObservables() {
        if (activitySubscriptions == null) {
            activitySubscriptions = new CompositeDisposable();
        }
    }
    //endregion

    //region Utility Methods to display fragments
    public void replaceFragment(BaseFragment fragment, boolean addToBackStack) {
        replaceFragment(fragment, addToBackStack, true);
    }

    public void replaceFragment(BaseFragment fragment, boolean addToBackStack, boolean animate) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (animate) {
//                transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
            }
            transaction.replace(getFragmentRoot(), fragment, fragment.getFragmentTag());
            Log.i(TAG, "Navigate to " + fragment.getFragmentTag());

            if (addToBackStack) {
                transaction.addToBackStack(fragment.getFragmentTag());
            }
            transaction.commit();
        }
    }

    protected int getFragmentRoot() {
        return R.id.container;
    }
    //endregion

    //region Utility Methods to handle loading
    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this, R.style.ProgressBarDialogStyle);
            progressDialog.setMessage(getString(R.string.loading));// default message
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * Allow to dismiss dialog when parent activity is destroyed
     */
    protected void dismissProgressDialog() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    // Get the Context that was used to create the dialog
                    Context context = ((ContextWrapper) progressDialog.getContext()).getBaseContext();

                    // If the Context used here was an activity AND it hasn't been or destroyed then dismiss it
                    if (context instanceof BaseActivity) {
                        if (!((BaseActivity) context).isDestroyed()) {
                            progressDialog.dismiss();
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                }
            }
        }, 1000);
    }
    //endregion
}
