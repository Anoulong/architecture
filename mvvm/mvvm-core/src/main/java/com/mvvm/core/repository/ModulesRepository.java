package com.mvvm.core.repository;


import com.mvvm.core.local.ApplicationDatabase;
import com.mvvm.core.local.module.Module;
import com.mvvm.core.manager.EndpointManager;
import com.mvvm.core.remote.ApiService;
import com.mvvm.core.service.NetworkConnectivityService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Repository that handles Module objects. Contains business logic.
 */
public class ModulesRepository extends BaseRepository {

    private static final String TAG = ModulesRepository.class.getSimpleName();

    private ApiService apiService;
    private ApplicationDatabase applicationDatabase;
    private EndpointManager endpointManager;
    private NetworkConnectivityService networkConnectivityService;
    private BehaviorSubject<List<Module>> modulesObservable = BehaviorSubject.createDefault(new ArrayList<Module>());

    public ModulesRepository(ApplicationDatabase applicationDatabase,
                             ApiService apiService,
                             EndpointManager endpointManager,
                             NetworkConnectivityService networkConnectivityService) {
        super();
        this.apiService = apiService;
        this.applicationDatabase = applicationDatabase;
        this.endpointManager = endpointManager;
        this.networkConnectivityService = networkConnectivityService;
    }

    /**
     * If no internet just retrieve local data otherwise
     * if on mobile or wifi pull latest data from remote API
     *
     * @return Observable of List<Module>
     */
    public Flowable<List<Module>> loadModules() {
        addDisposable(networkConnectivityService.getConnectionTypeObservable()
                .filter(type -> !type.equals(NetworkConnectivityService.ConnectionType.TYPE_NO_INTERNET))
                .subscribe(status -> fetchRemoteData(), modulesObservable::onError));

        return retrieveLocalData();
    }

    /**
     *  Load data from local Room Database
     * @return
     */
    @Override
    public Flowable<List<Module>> retrieveLocalData() {
        addDisposable(applicationDatabase
                .moduleDao()
                .loadAllModules()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(localData -> modulesObservable.onNext(localData), modulesObservable::onError));

        return modulesObservable.toFlowable(BackpressureStrategy.LATEST);
    }

    /**
     * Pull data from remote API and also update the local database
     */
    @Override
    public void fetchRemoteData() {
        addDisposable(apiService
                .fetchModules(endpointManager.getAuthorizationToken(), endpointManager.getAppId())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(remoteData -> applicationDatabase.moduleDao().insertAll(remoteData), modulesObservable::onError));
    }
}
