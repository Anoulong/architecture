package com.mvvm.core.service;

import io.reactivex.Observable;

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
public interface NetworkConnectivityService {

    enum ConnectionType {
        TYPE_WIFI,
        TYPE_MOBILE,
        TYPE_NO_INTERNET
    }

    ConnectionType getConnectionType();

    Observable<ConnectionType> getConnectionTypeObservable();

    void setConnectionType(ConnectionType newConnectionType);
}
