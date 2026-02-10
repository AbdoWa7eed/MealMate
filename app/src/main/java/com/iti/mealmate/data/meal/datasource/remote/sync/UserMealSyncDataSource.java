package com.iti.mealmate.data.meal.datasource.remote.sync;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserMealSyncDataSource {

    Completable uploadFavorites(String uid, List<Meal> favorites);
    Completable uploadPlans(String uid, List<DayPlan> plans);

    Single<List<Meal>> downloadFavorites(String uid);
    Single<List<DayPlan>> downloadPlans(String uid);
}