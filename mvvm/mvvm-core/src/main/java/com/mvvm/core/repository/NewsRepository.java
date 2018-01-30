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
 * Created by Anou Chanthavong on 2018-01-30.
 ******************************************************************************/
package com.mvvm.core.repository;


import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.local.module.ModuleEntity;
import com.mvvm.core.local.news.NewsEntity;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.service.NetworkConnectivityService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Repository that handles NewsEntity objects. Contains business logic.
 */
public class NewsRepository  extends BaseRepository  {

    private static final String TAG = ModuleRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private EndpointManager endpointManager;
    private NetworkConnectivityService networkConnectivityService;
    private BehaviorSubject<List<NewsEntity>> modulesObservable = BehaviorSubject.createDefault(new ArrayList<NewsEntity>());

    @Override
    public <LocalData> LocalData retrieveLocalData() {
        return null;
    }

    @Override
    public void fetchRemoteData() {

    }
}
