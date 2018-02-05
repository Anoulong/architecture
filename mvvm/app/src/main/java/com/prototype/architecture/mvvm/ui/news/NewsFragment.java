package com.prototype.architecture.mvvm.ui.news;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mvvm.core.local.news.News;
import com.mvvm.core.viewmodel.NewsViewModel;
import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.coordinator.Coordinator;
import com.prototype.architecture.mvvm.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
 * Created by Anou Chanthavong on 2017-12-16.
 ******************************************************************************/
public class NewsFragment extends BaseFragment {

    private static final String TAG = NewsFragment.class.getSimpleName();

    // Injecting Dependencies
    @Inject
    ViewModelProvider.Factory viewModelFactory; //Help to create ViewModel
    @Inject
    Coordinator coordinator;// Handle navigation

    // Data Model to fill the views with
    private NewsViewModel viewModel;

    TextView newsTextView;

    public static NewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RcaApplication.getApplicationComponent(getActivity()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setupView(view);
    }


    @Override
    public void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel.class);
    }

    @Override
    protected void setupObservables() {
        super.setupObservables();
        addDisposable(viewModel.getNews()
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(data -> updateData(data), error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }));
    }

    @Override
    protected String getFragmentTitle() {
        return TAG;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }


    private void setupView(View v) {
        newsTextView = v.findViewById(R.id.newsTextView);
    }

    private void updateData(List<News> newsEntities) {
        if (newsEntities != null && !newsEntities.isEmpty()) {
            String news = "";
            for (News n : newsEntities) {
                news += n.getTitle().getEn() + "\n";
            }
            newsTextView.setText(news);

            Log.d(TAG, "updateData: " + newsEntities.size() + " news");
        }
    }
}
