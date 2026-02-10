package com.iti.mealmate.data.meal.repo.favorite;


import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.remote.sync.UserMealSyncDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteLocalDataSource favoriteLocalDataSource;
    private final MealLocalDataSource mealLocalDataSource;
    private final UserMealSyncDataSource syncDataSource;
    private final AppConnectivityManager connectivityManager;

    public FavoriteRepositoryImpl(
            FavoriteLocalDataSource favoriteLocalDataSource,
            MealLocalDataSource mealLocalDataSource,
            UserMealSyncDataSource syncDataSource,
            AppConnectivityManager connectivityManager
    ) {
        this.favoriteLocalDataSource = favoriteLocalDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        this.syncDataSource = syncDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Flowable<List<Meal>> getAllFavoriteIds(String uid) {
        return getLocalFavorites()
                .flatMap(favorites -> {
                    if (!favorites.isEmpty()) {
                        return Flowable.just(favorites);
                    }
                    return fetchAndCacheRemoteFavorites(uid);
                })
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<Boolean> isFavorite(String mealId) {
        return favoriteLocalDataSource.isFavorite(mealId)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable addToFavorites(Meal meal) {
        return mealLocalDataSource.isMealExists(meal.getId())
                .flatMapCompletable(exists -> {
                    if (exists) {
                        return favoriteLocalDataSource.addToFavorites(meal.getId());
                    } else {
                        meal.setFavorite(true);
                        return mealLocalDataSource.insertMeal(MealMapper.domainToCachedEntity(meal, CacheType.NONE))
                                .andThen(favoriteLocalDataSource.addToFavorites(meal.getId()));
                    }
                })
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable removeFromFavorites(Meal meal) {
        return favoriteLocalDataSource.removeFromFavorites(meal.getId())
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }


    private Flowable<List<Meal>> getLocalFavorites() {
        return favoriteLocalDataSource.getUserFavorites()
                .map(MealMapper::cachedEntitiesToDomain);
    }

    private Flowable<List<Meal>> fetchAndCacheRemoteFavorites(String uid) {
        return RxTask.withConnectivity(
                        syncDataSource.downloadFavorites(uid)
                                .flatMapCompletable(this::cacheRemoteFavorites)
                                .andThen(getLocalFavorites().firstOrError()), // Single<List<Meal>>
                        connectivityManager
                )
                .toFlowable()
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    private Completable cacheRemoteFavorites(List<Meal> remoteFavorites) {
        return Flowable.fromIterable(remoteFavorites)
                .concatMapCompletable(this::addToFavorites);
    }
}