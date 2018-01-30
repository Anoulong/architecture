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

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.local.ModuleDao;
import com.mvvm.core.local.ModuleEntity;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.service.NetworkConnectivityService;
import com.mvvm.core.viewmodel.ModuleListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subscribers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
//        applicationDatabase = mock(ApplicationDatabase.class);
//        moduleDao = mock(ModuleDao.class);
//        apiService = mock(ApiService.class);
//        endpointManager = mock(EndpointManager.class);
//        networkConnectivityService = mock(NetworkConnectivityService.class);

//        moduleRepository = new ModuleRepository(applicationDatabase, apiService, endpointManager, networkConnectivityService);
        moduleSubscriber = TestSubscriber.create();
    }

    @Test
    public void testRetrieveLocalData_WhenMobileNetworkOn() throws Exception {
        // Given that the applicationDatabase returns an empty list of ModuleEntities

        //Simulate network status
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));
        when(endpointManager.getAuthorizationToken()).thenReturn("1234abcd");
        when(endpointManager.getAppId()).thenReturn("1234abcd");
        when(applicationDatabase.moduleDao()).thenReturn(moduleDao);
        when(moduleDao.loadAllModules()).thenReturn(Flowable.<List<ModuleEntity>>empty());

        moduleRepository.retrieveLocalData().subscribe(moduleSubscriber);
        moduleSubscriber.assertNoErrors();

        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

    @Test
    public void testFetchRemoteData_WhenMobileNetworkOn() throws Exception {
        // Given that the ApiService returns a list of ModuleEntities
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));
        when(apiService.fetchModules("1234abcd", "1234abcd")).thenReturn(Flowable.just(Collections.<ModuleEntity>singletonList(new ModuleEntity())));

        moduleRepository.fetchRemoteData();
        moduleSubscriber.assertNoErrors();


    }

//    @Test
//    public void goToNetwork() {
//        MutableLiveData<User> dbData = new MutableLiveData<>();
//        when(userDao.findByLogin("foo")).thenReturn(dbData);
//        User user = TestUtil.createUser("foo");
//        LiveData<ApiResponse<User>> call = ApiUtil.successCall(user);
//        when(githubService.getUser("foo")).thenReturn(call);
//        Observer<Resource<User>> observer = mock(Observer.class);
//
//        repo.loadUser("foo").observeForever(observer);
//        verify(githubService, never()).getUser("foo");
//        MutableLiveData<User> updatedDbData = new MutableLiveData<>();
//        when(userDao.findByLogin("foo")).thenReturn(updatedDbData);
//        dbData.setValue(null);
//        verify(githubService).getUser("foo");
//    }
//
//    @Test
//    public void dontGoToNetwork() {
//        MutableLiveData<User> dbData = new MutableLiveData<>();
//        User user = TestUtil.createUser("foo");
//        dbData.setValue(user);
//        when(userDao.findByLogin("foo")).thenReturn(dbData);
//        Observer<Resource<User>> observer = mock(Observer.class);
//        repo.loadUser("foo").observeForever(observer);
//        verify(githubService, never()).getUser("foo");
//        verify(observer).onChanged(Resource.success(user));
//    }
}