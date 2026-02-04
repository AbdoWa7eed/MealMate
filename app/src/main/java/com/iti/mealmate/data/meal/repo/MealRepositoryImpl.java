package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.util.RxTask;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImpl implements MealRepository {

    private final MealRemoteDataSource remoteDataSource;
    private final AppConnectivityManager connectivityManager;

    public MealRepositoryImpl(MealRemoteDataSource remoteDataSource,
                              AppConnectivityManager connectivityManager) {
        this.remoteDataSource = remoteDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Single<Meal> getMealOfTheDay() {
        return RxTask.withConnectivity(
                remoteDataSource.getMealOfTheDay(),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<Meal>> getMealsByIngredient(String ingredient) {
        return RxTask.withConnectivity(
                remoteDataSource.getMealsByIngredient(ingredient),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<Meal>> getSuggestedMeals() {
        return RxTask.withConnectivity(
                remoteDataSource.getSuggestedMeals(),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<Meal>> searchMealsByName(String name) {
        return RxTask.withConnectivity(
                remoteDataSource.searchMealsByName(name),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }
}
