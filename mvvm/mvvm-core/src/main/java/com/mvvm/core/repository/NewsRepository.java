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
import com.mvvm.core.local.news.News;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.service.NetworkConnectivityService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Repository that handles News objects. Contains business logic.
 */
public class NewsRepository  extends BaseRepository  {

    private static final String TAG = NewsRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private EndpointManager endpointManager;
    private NetworkConnectivityService networkConnectivityService;
    private BehaviorSubject<List<News>> newsObservable = BehaviorSubject.createDefault(new ArrayList<News>());
    private String moduleEid;

    public NewsRepository(ApplicationDatabase applicationDatabase,
                             ApiService apiService,
                             EndpointManager endpointManager,
                             NetworkConnectivityService networkConnectivityService, String moduleEid) {
        super();
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.endpointManager = endpointManager;
        this.networkConnectivityService = networkConnectivityService;
        this.moduleEid = moduleEid;
    }

    /**
     * If no internet just retrieve local data otherwise
     * if on mobile or wifi pull latest data from remote API
     *
     * @return Observable of List<News>
     */
    public Flowable<List<News>> loadNews() {
        addDisposable(networkConnectivityService.getConnectionTypeObservable()
                .filter(type -> !type.equals(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET))
                .subscribe(status -> fetchRemoteData(), newsObservable::onError));

        return retrieveLocalData();
    }

    /**
     *  Load data from local Room Database
     * @return
     */
    @Override
    public Flowable<List<News>> retrieveLocalData() {
        addDisposable(applicationDatabase
                .newsDao()
                .loadAllNews()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(localData -> newsObservable.onNext(localData), newsObservable::onError));

        return newsObservable.toFlowable(BackpressureStrategy.LATEST);
    }

    /**
     * Pull data from remote API and also update the local database
     */
    @Override
    public void fetchRemoteData() {
        addDisposable(apiService
                .fetchNews(endpointManager.getAuthorizationToken(), endpointManager.getAppId(), moduleEid)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(remoteData -> applicationDatabase.newsDao().insertAll(remoteData), newsObservable::onError));
    }
}
