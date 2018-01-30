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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModuleRepositoryTest {

    private ApplicationDatabase applicationDatabase;
    private ApiService apiService;
    private ModuleRepository moduleRepository;
    private EndpointManager endpointManager;
    private NetworkConnectivityService networkConnectivityService;

    @Before
    public void setup() {
        applicationDatabase = mock(ApplicationDatabase.class);
        apiService = mock(ApiService.class);
        endpointManager = mock(EndpointManager.class);
        networkConnectivityService = mock(NetworkConnectivityService.class);

        moduleRepository = new ModuleRepository(applicationDatabase, apiService, endpointManager, networkConnectivityService);

    }

    @Test
    public void loadModules() {
        // Given that the applicationDatabase returns an empty list of ModuleEntities
        when(networkConnectivityService.getConnectionTypeObservable()).thenReturn(Observable.just(NetworkConnectivityService.ConnectionType.TYPE_MOBILE));
//        when(applicationDatabase.moduleDao().loadAllModules()).thenReturn(Flowable.<List<ModuleEntity>>empty());

        moduleRepository.loadModules();
        verify(applicationDatabase.moduleDao()).loadAllModules();
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