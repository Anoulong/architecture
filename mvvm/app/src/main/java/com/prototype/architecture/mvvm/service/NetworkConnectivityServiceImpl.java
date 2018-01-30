package com.prototype.architecture.mvvm.service;

import android.util.Log;

import com.mvvm.core.service.NetworkConnectivityService;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

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
public class NetworkConnectivityServiceImpl implements NetworkConnectivityService {

    private static final String TAG = NetworkConnectivityServiceImpl.class.getSimpleName();

    private BehaviorSubject<ConnectionType> connectionTypeObservable = BehaviorSubject.createDefault(ConnectionType.TYPE_NO_INTERNET);

    public NetworkConnectivityServiceImpl() {
    }

    @Override
    public ConnectionType getConnectionType() {
        return connectionTypeObservable.getValue();
    }

    @Override
    public Observable<ConnectionType> getConnectionTypeObservable() {
        return connectionTypeObservable;
    }

    @Override
    public void setConnectionType(ConnectionType newConnectionType) {
        if (newConnectionType != getConnectionType()) {
            Log.v(TAG, String.format("Network connection status: %s", newConnectionType.name()));
        }
        connectionTypeObservable.onNext(newConnectionType);
    }
}
