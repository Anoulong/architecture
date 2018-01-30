package com.mvvm.core.remote;

import com.mvvm.core.local.module.ModuleEntity;
import com.mvvm.core.local.news.NewsEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

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
 * Created by Anou Chanthavong on 2018-01-29.
 ******************************************************************************/
public interface ApiService {
//https://api-dev.quickseries.com/api-docs-mobile/#/

    @GET("apps/{appId}/custom-modules")
    Flowable<List<ModuleEntity>> fetchModules(@Header("Authorization") String authorizationToken, @Path("appId") String appId);

    @GET("apps/{appId}/news/{moduleEid}/posts")
    Flowable<List<NewsEntity>> fetchNews(@Header("Authorization") String authorizationToken, @Path("appId") String appId, @Path("moduleEid") String moduleEid);
}
