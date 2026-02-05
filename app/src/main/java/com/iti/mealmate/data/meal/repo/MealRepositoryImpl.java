package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;

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
    public Single<List<MealLight>> getMealsByIngredient(String ingredient) {
        return RxTask.withConnectivity(
                remoteDataSource.getMealsByIngredient(ingredient),
                connectivityManager
        )
        .map(MealMapper::toLightList)
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<Meal>> getSuggestedMeals() {
        return RxTask.withConnectivity(
                remoteDataSource.getSuggestedMeals(),
                connectivityManager
        )
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<MealLight>> searchMealsByName(String name) {
        return RxTask.withConnectivity(
                remoteDataSource.searchMealsByName(name),
                connectivityManager
        )
        .map(MealMapper::toLightList)
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<MealLight>> getMealsByCategory(String category) {
        return RxTask.withConnectivity(
                remoteDataSource.getMealsByCategory(category),
                connectivityManager
        )
        .map(MealMapper::toLightList)
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<MealLight>> getMealsByCountry(String country) {
        return RxTask.withConnectivity(
                remoteDataSource.getMealsByCountry(country),
                connectivityManager
        )
        .map(MealMapper::toLightList)
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<Meal> getMealById(String id) {
        return RxTask.withConnectivity(
                remoteDataSource.getMealById(id),
                connectivityManager
        )
        .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }
}
