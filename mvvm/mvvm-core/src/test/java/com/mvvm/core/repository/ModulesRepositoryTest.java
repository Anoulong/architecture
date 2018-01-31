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
import com.mvvm.core.local.module.Module;
import com.mvvm.core.local.module.ModuleDao;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModulesRepositoryTest {

    @InjectMocks
    private ModulesRepository moduleRepository;

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
    private ArgumentCaptor<List<Module>> mListModulesArgumentCaptor; //Capture return

    private TestSubscriber<List<Module>> moduleSubscriber; // Subscribe


    @Before
    public void setup() {
        when(endpointManager.getAuthorizationToken()).thenReturn("AuthorizationToken");
        when(endpointManager.getAppId()).thenReturn("AppId");
        moduleSubscriber = TestSubscriber.create();
    }

    @Test
    public void testRetrieveLocalData() throws Exception {
        // Given that the applicationDatabase returns an empty list of ModuleEntities

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.<List<Module>>empty());

        moduleRepository.retrieveLocalData().subscribe(moduleSubscriber);

        moduleSubscriber.assertNoErrors();
        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

    @Test
    public void testLoadModulesEmpty() throws Exception {
        // Given that the applicationDatabase returns a list of ModuleEntities
        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));

        //Simulate ApiService call
        when(apiService.fetchModules(endpointManager.getAuthorizationToken(), endpointManager.getAppId())).thenReturn(Flowable.<List<Module>>empty());

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.<List<Module>>empty());

        moduleRepository.loadModules().subscribe(moduleSubscriber);

        moduleSubscriber.assertNoErrors();
        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

    @Test
    public void testLoadModules() throws Exception {
        // Given that the applicationDatabase returns a list of 1 Module

        Module module = mock(Module.class);


        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));


        //Simulate ApiService call
        when(apiService.fetchModules(endpointManager.getAuthorizationToken(), endpointManager.getAppId())).thenReturn(Flowable.just(Collections.<Module>singletonList(module)));

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.just(Collections.<Module>singletonList(module)));

        //simulate load module
        moduleRepository.loadModules().subscribe(moduleSubscriber);

        verify(moduleDao).insertAll(mListModulesArgumentCaptor.capture());

        assertThat(mListModulesArgumentCaptor.getValue()).isNotEmpty();

        moduleSubscriber.assertNoErrors();

    }

    @Test
    public void testLoadModulesNoInternet() throws Exception {
        // Given that the applicationDatabase returns a list of 1 Module

        Module module1 = mock(Module.class);
        Module module2 = mock(Module.class);
        Module module3 = mock(Module.class);

        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET));

        //Simulate Database
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.just(Arrays.asList((Module) module1, (Module) module2, (Module)module3)));

        moduleRepository.loadModules().subscribe(moduleSubscriber);

        verify(apiService, times(0)).fetchModules(anyString(), anyString());
        verify(moduleDao, times(0)).insertAll(any());

        moduleSubscriber.assertNoErrors();

    }

}