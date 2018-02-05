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

package com.mvvm.core.viewmodel;

import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.local.module.Module;
import com.mvvm.core.local.module.ModuleDao;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.repository.ModulesRepository;
import com.mvvm.core.service.NetworkConnectivityService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ModulesViewModelTest {

    private ModulesViewModel moduleListViewModel;

    @Mock
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
        moduleListViewModel = new ModulesViewModel(moduleRepository);
        moduleRepository = new ModulesRepository(applicationDatabase, apiService, endpointManager, networkConnectivityService);
        moduleSubscriber = TestSubscriber.create();
    }

    @Test
    public void testRetrieveLocalData() throws Exception {
        // Given that the applicationDatabase returns an empty list of ModuleEntities

        moduleListViewModel.getModules().subscribe(moduleSubscriber);

//        verify(moduleRepository).loadModules();

        moduleSubscriber.assertNoErrors();
        moduleSubscriber.assertValue(Collections.EMPTY_LIST);

    }

}