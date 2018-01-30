package com.prototype.architecture.mvvm.dependency;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import com.prototype.architecture.mvvm.viewmodel.ModuleListViewModelImpl;
import com.prototype.architecture.mvvm.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ModuleListViewModelImpl.class)
    public abstract ViewModel bindModuleListViewModel(ModuleListViewModelImpl moduleListViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(NewsViewModel.class)
//    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);


    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}