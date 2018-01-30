package com.prototype.architecture.mvvm.ui.shelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.ui.base.BaseFragment;

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
public class ShelfFragment extends BaseFragment {

    private static final String TAG = ShelfFragment.class.getSimpleName();

    public static ShelfFragment newInstance() {

        Bundle args = new Bundle();

        ShelfFragment fragment = new ShelfFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shelf, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected String getFragmentTitle() {
        return null;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
