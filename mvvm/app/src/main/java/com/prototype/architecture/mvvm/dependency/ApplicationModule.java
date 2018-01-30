package com.prototype.architecture.mvvm.dependency;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.repository.ModuleRepository;
import com.mvvm.core.service.NetworkConnectivityService;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.coordinator.Coordinator;
import com.prototype.architecture.mvvm.manager.EndpointManagerImpl;
import com.prototype.architecture.mvvm.service.NetworkConnectivityServiceImpl;
import com.prototype.architecture.mvvm.service.NetworkStateBroadcastReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


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
@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    private RcaApplication application;

    public ApplicationModule(RcaApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideRcaApplication() {
        return application;
    }

    @Singleton
    @Provides
    ApplicationDatabase provideApplicationDatabase() {
        return Room.databaseBuilder(application, ApplicationDatabase.class, ApplicationDatabase.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    public ApiService providesApiService(EndpointManager endpointManager) {
        return new Retrofit.Builder()
                .baseUrl(endpointManager.getBaseUrlV3())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    public ModuleRepository providesRcaRepository(ApplicationDatabase applicationDatabase, ApiService apiService, EndpointManager endpointManager, NetworkConnectivityService networkConnectivityService) {
        return new ModuleRepository(applicationDatabase, apiService, endpointManager, networkConnectivityService);
    }

    @Provides
    @Singleton
    NetworkConnectivityService provideNetworkConnectivityService() {
        return new NetworkConnectivityServiceImpl();
    }

    @Provides
    @Singleton
    NetworkStateBroadcastReceiver provideNetworkStateBroadcastReceiver(NetworkConnectivityService networkConnectivityService) {
        return new NetworkStateBroadcastReceiver(networkConnectivityService);
    }

    @Provides
    @Singleton
    EndpointManager provideEndpointManager() {
        return new EndpointManagerImpl(application);
    }

    @Provides
    @Singleton
    Coordinator provideCoordinator() {
        return new Coordinator();
    }
}