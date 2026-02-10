package com.iti.mealmate.data.meal.repo.favorite;

import com.iti.mealmate.data.meal.model.entity.Meal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface FavoriteRepository {

    Flowable<List<Meal>> getAllFavoriteIds(String uid);

    Single<Boolean> isFavorite(String mealId);

    Completable addToFavorites(Meal meal);

    Completable removeFromFavorites(Meal meal);

}
