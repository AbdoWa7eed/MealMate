package com.iti.mealmate.data.meal.repo.favorite;


import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteLocalDataSource favoriteLocalDataSource;

    public FavoriteRepositoryImpl(FavoriteLocalDataSource favoriteLocalDataSource) {
        this.favoriteLocalDataSource = favoriteLocalDataSource;
    }

    @Override
    public Flowable<List<Meal>> getAllFavoriteIds() {
        return favoriteLocalDataSource.getUserFavorites()
                .map(MealMapper::cachedEntitiesToDomain)
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return favoriteLocalDataSource.isFavorite(mealId)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable addToFavorites(String mealId) {
        return favoriteLocalDataSource.addToFavorites(mealId)
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));

    }

    @Override
    public Completable removeFromFavorites(String mealId) {
        return favoriteLocalDataSource.removeFromFavorites(mealId)
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }


}
