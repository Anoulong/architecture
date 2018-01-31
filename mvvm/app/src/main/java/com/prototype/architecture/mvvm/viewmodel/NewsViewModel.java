package com.prototype.architecture.mvvm.viewmodel;

import com.mvvm.core.repository.ModulesRepository;
import com.mvvm.core.repository.NewsRepository;
import com.mvvm.core.viewmodel.CoreModulesViewModel;
import com.mvvm.core.viewmodel.CoreNewsViewModel;

import javax.inject.Inject;

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
 * Created by Anou Chanthavong on 2018-01-31.
 ******************************************************************************/
public class NewsViewModel extends CoreNewsViewModel {

    @Inject
    public NewsViewModel(NewsRepository newsRepository) {
        super(newsRepository);
    }
}
