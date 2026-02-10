package com.iti.mealmate.data.meal.repo.sync;

import io.reactivex.rxjava3.core.Completable;

public interface SyncRepository {

    Completable syncAll(String uid);
    Completable syncFavorites(String uid);
    Completable syncPlannedMeals(String uid);

}
