package com.iti.mealmate.data.meal.datasource.local.datasource.meal;

import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

public interface MealLocalDataSource {
    Completable insertMeals(List<MealEntity> meals);
    Completable insertMeal(MealEntity meal);
    Flowable<MealEntity> getMealById(String mealId);
    Flowable<List<MealEntity>> getMealsByType(CacheType type);
    Single<Boolean> isMealExists(String mealId);
}
