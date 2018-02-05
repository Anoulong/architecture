package com.prototype.architecture.mvvm.dependency;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import com.mvvm.core.viewmodel.ModulesViewModel;
import com.mvvm.core.viewmodel.NewsViewModel;
import com.prototype.architecture.mvvm.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ModulesViewModel.class)
    public abstract ViewModel bindModuleListViewModel(ModulesViewModel moduleListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}