/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvvm.core.repository;

import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.local.module.ModuleDao;
import com.mvvm.core.local.module.ModuleEntity;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.service.NetworkConnectivityService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModuleRepositoryTest {

    @InjectMocks
    private ModuleRepository moduleRepository;

    @Mock
    private ApplicationDatabase applicationDatabase;
    @Mock
    private ApiService apiService;
    @Mock
    private ModuleDao moduleDao;
    @Mock
    private EndpointManager endpointManager;
    @Mock
    private NetworkConnectivityService networkConnectivityService;

    @Captor
    private ArgumentCaptor<List<ModuleEntity>> mListModulesArgumentCaptor; //Capture return

    private TestSubscriber<List<ModuleEntity>> moduleSubscriber; // Subscribe


    @Before
    public void setup() {
        moduleSubscriber = TestSubscriber.create();
    }

    @Test
    public void testRetrieveLocalData_Empty_WhenMobileNetworkOn() throws Exception {
        // Given that the applicationDatabase returns an empty list of ModuleEntities

        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));

        //Simulate endpoint
        when(endpointManager.getAuthorizationToken()).thenReturn("1234abcd");
        when(endpointManager.getAppId()).thenReturn("1234abcd");

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.<List<ModuleEntity>>empty());

        moduleRepository.retrieveLocalData().subscribe(moduleSubscriber);

        moduleSubscriber.assertNoErrors();
        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

    @Test
    public void testRetrieveLocalData_1Module_WhenMobileNetworkOn() throws Exception {
        // Given that the applicationDatabase returns an empty list of ModuleEntities

        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));

        //Simulate endpoint
        when(endpointManager.getAuthorizationToken()).thenReturn("1234abcd");
        when(endpointManager.getAppId()).thenReturn("1234abcd");

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.<List<ModuleEntity>>empty());

        moduleRepository.retrieveLocalData().subscribe(moduleSubscriber);

        moduleSubscriber.assertNoErrors();
        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

}