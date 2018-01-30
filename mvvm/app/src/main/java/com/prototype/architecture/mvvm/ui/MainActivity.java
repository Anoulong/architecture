package com.prototype.architecture.mvvm.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.mvvm.core.common.utils.StringUtils;
import com.mvvm.core.local.module.ModuleEntity;
import com.mvvm.core.viewmodel.ModuleListViewModel;
import com.prototype.architecture.mvvm.R;
import com.prototype.architecture.mvvm.RcaApplication;
import com.prototype.architecture.mvvm.coordinator.Coordinator;
import com.prototype.architecture.mvvm.ui.base.BaseActivity;
import com.prototype.architecture.mvvm.ui.drawer.DrawerAdapter;
import com.prototype.architecture.mvvm.viewmodel.ModuleListViewModelImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Injecting Dependencies
    @Inject
    ViewModelProvider.Factory viewModelFactory; //Help to create ViewModel
    @Inject
    Coordinator coordinator;// Handle navigation

    // Data Model to fill the views with
    private ModuleListViewModel viewModel;

    //Views
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private DrawerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static Intent intent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RcaApplication.getApplicationComponent(this).inject(this);
        setContentView(R.layout.activity_main);

        this.setupView();
        this.setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ModuleListViewModelImpl.class);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void setupObservables() {
        super.setupObservables();
        addDisposable(viewModel.getModules()
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(data -> updateData(data), error -> {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }));
    }

    //region Methods to help to setup views, bind views with view data model and prepare listeners
    private void setupView() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        mRecyclerView = findViewById(R.id.drawerRecyclerView);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mAdapter = new DrawerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupListener() {
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mAdapter.setListener((view, item, position) -> {
            coordinator.didDrawerItemSelected(MainActivity.this, item);
            mAdapter.setSelection(position);
            drawer.closeDrawer(GravityCompat.START);
        });
    }

    private void updateData(List<ModuleEntity> moduleEntities) {
        if (moduleEntities != null && !moduleEntities.isEmpty()) {
            List<DrawerAdapter.DrawerItem> items = new ArrayList<>();
            items.add(new DrawerAdapter.DrawerItem(DrawerAdapter.DrawerItem.ItemType.header));
            for (ModuleEntity m : moduleEntities) {
                items.add(new DrawerAdapter.DrawerItem(DrawerAdapter.DrawerItem.ItemType.module, m));
            }
            items.add(new DrawerAdapter.DrawerItem(DrawerAdapter.DrawerItem.ItemType.settings, getString(R.string.action_settings)));

            mAdapter.setData(items);
            mAdapter.notifyDataSetChanged();
            // initialize to a selected module for the drawer and display the corresponding screen
            if (StringUtils.isNotBlank(coordinator.getLatestSelectedModuleEid())) {
                int selectedPosition = mAdapter.getPositionForModule(coordinator.getLatestSelectedModuleEid());
                if (selectedPosition >= 0 && selectedPosition < mAdapter.getData().size()) {
                    coordinator.didDrawerItemSelected(MainActivity.this, mAdapter.getItem(selectedPosition));
                    mAdapter.setSelection(selectedPosition);
                }
            }
            Log.d(TAG, "onCreate: " + moduleEntities.size());
        }
    }
    //endregion
}
