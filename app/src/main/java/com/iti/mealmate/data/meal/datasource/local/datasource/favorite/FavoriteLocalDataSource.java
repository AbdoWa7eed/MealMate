package com.iti.mealmate.data.meal.datasource.local.datasource.favorite;

import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface FavoriteLocalDataSource {

    Flowable<List<MealEntity>> getUserFavorites();

    Completable addToFavorites(String mealId);

    Completable removeFromFavorites(String mealId);

    Single<Boolean> isFavorite(String mealId);
}
