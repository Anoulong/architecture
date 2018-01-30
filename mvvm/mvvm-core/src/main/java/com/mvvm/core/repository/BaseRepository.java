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
package com.mvvm.core.repository;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Parent Repository that handles Module objects. Contains business logic.
 */
public abstract class BaseRepository {
    private static final String TAG = BaseRepository.class.getSimpleName();

    protected CompositeDisposable repositorySubscriptions;

    public BaseRepository() {
        Log.d(TAG, "Initialize repositorySubscriptions" );
        this.repositorySubscriptions = new CompositeDisposable();
    }

    protected final void addDisposable(Disposable subscription) {
        repositorySubscriptions.add(subscription);
    }

    public void clearObservables(){
        Log.d(TAG, TAG + " cleared");
        if (repositorySubscriptions != null && !repositorySubscriptions.isDisposed()) {
            repositorySubscriptions.dispose();
        }
    }

    /**
     * Load data from local Room Database
     * @param <LocalData>
     * @return
     */
    public abstract <LocalData> LocalData retrieveLocalData ();

    /**
     * Pull data from remote API and also update the local database
     */
    public abstract void fetchRemoteData ();
}
