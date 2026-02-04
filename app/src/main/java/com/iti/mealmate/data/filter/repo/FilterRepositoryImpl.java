package com.iti.mealmate.data.filter.repo;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.data.filter.datasource.remote.FilterRemoteDataSource;
import com.iti.mealmate.data.filter.model.entity.FilterItem;
import com.iti.mealmate.data.filter.model.mapper.FilterMapper;
import com.iti.mealmate.core.util.RxTask;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FilterRepositoryImpl implements FilterRepository {

    private final FilterRemoteDataSource remoteDataSource;
    private final AppConnectivityManager connectivityManager;

    public FilterRepositoryImpl(FilterRemoteDataSource remoteDataSource,
                                AppConnectivityManager connectivityManager) {
        this.remoteDataSource = remoteDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Single<List<FilterItem>> getCategoryFilters() {
        return RxTask.withConnectivity(
                        remoteDataSource.getCategories(),
                        connectivityManager
                )
                .map(FilterMapper::fromCategoryList)
                .onErrorResumeNext(throwable ->
                        Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<FilterItem>> getIngredientFilters() {
        return RxTask.withConnectivity(
                        remoteDataSource.getIngredients(),
                        connectivityManager
                )
                .map(FilterMapper::fromIngredientList)
                .onErrorResumeNext(throwable ->
                        Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<FilterItem>> getCountryFilters() {
        return RxTask.withConnectivity(
                        remoteDataSource.getCountries(),
                        connectivityManager
                )
                .map(FilterMapper::fromCountryList)
                .onErrorResumeNext(throwable ->
                        Single.error(AppErrorHandler.handle(throwable)));
    }
}


