package com.iti.mealmate.data.meal.datasource.local.datasource.favorite;

import com.iti.mealmate.data.meal.datasource.local.dao.MealDao;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteLocalDataSourceImpl implements FavoriteLocalDataSource {

    private final MealDao mealDao;

    public FavoriteLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public Flowable<List<MealEntity>> getUserFavorites() {
        return mealDao.getFavorites();
    }

    @Override
    public Completable addToFavorites(String mealId) {
        return mealDao.addToFavorites(mealId);
    }

    @Override
    public Completable removeFromFavorites(String mealId) {
        return mealDao.removeFromFavorites(mealId);
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return mealDao.isFavorite(mealId).onErrorReturn(throwable -> false);
    }
}