package com.mvvm.core.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mvvm.core.local.module.Module;
import com.mvvm.core.repository.ModulesRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

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
 * Created by Anou Chanthavong on 2017-12-04.
 ******************************************************************************/
public class CoreModulesViewModel extends ViewModel  {
    private static final String TAG = CoreModulesViewModel.class.getSimpleName();

    private final ModulesRepository moduleRepository;

    public CoreModulesViewModel(ModulesRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * Expose the Observable Modules query so the UI can observe it.
     */
    public Flowable<List<Module>> getModules() {
        //Drop DB data if we can fetch item fast enough from the API
        //to avoid UI flickers
        return moduleRepository.loadModules().debounce(400, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, TAG + " cleared");
        moduleRepository.clearObservables();
    }
}
