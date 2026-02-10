package com.iti.mealmate.data.meal.datasource.remote.sync;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserMealSyncDataSourceImpl implements UserMealSyncDataSource {

    private final FirestoreUserHelper firestoreUserHelper;

    public UserMealSyncDataSourceImpl(FirestoreUserHelper firestoreUserHelper) {
        this.firestoreUserHelper = firestoreUserHelper;
    }

    @Override
    public Completable uploadFavorites(String uid, List<Meal> favorites) {
        return firestoreUserHelper.syncFavoriteMeals(uid, favorites);
    }

    @Override
    public Completable uploadPlans(String uid, List<DayPlan> plans) {
        return firestoreUserHelper.syncPlans(uid, plans);
    }

    @Override
    public Single<List<Meal>> downloadFavorites(String uid) {
        return firestoreUserHelper.getFavoriteMeals(uid);
    }

    @Override
    public Single<List<DayPlan>> downloadPlans(String uid) {
        return firestoreUserHelper.getPlans(uid);
    }
}