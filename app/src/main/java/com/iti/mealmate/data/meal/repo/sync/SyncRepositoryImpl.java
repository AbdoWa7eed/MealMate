package com.iti.mealmate.data.meal.repo.sync;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSource;
import com.iti.mealmate.data.meal.datasource.remote.sync.UserMealSyncDataSource;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.mapper.PlanMapper;
import java.time.LocalDate;
import io.reactivex.rxjava3.core.Completable;

public class SyncRepositoryImpl implements SyncRepository {

    private final FavoriteLocalDataSource favoriteLocalDataSource;
    private final PlanLocalDataSource planLocalDataSource;
    private final UserMealSyncDataSource syncDataSource;
    private final AppConnectivityManager connectivityManager;

    public SyncRepositoryImpl(
            FavoriteLocalDataSource favoriteLocalDataSource,
            PlanLocalDataSource planLocalDataSource,
            UserMealSyncDataSource syncDataSource,
            AppConnectivityManager connectivityManager
    ) {
        this.favoriteLocalDataSource = favoriteLocalDataSource;
        this.planLocalDataSource = planLocalDataSource;
        this.syncDataSource = syncDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Completable syncFavorites(String uid) {
        return favoriteLocalDataSource.getUserFavorites()
                .firstOrError()
                .flatMapCompletable(favorites -> {
                    if (favorites.isEmpty()) return Completable.complete();
                    return RxTask.withConnectivity(
                            syncDataSource.uploadFavorites(uid, MealMapper.cachedEntitiesToDomain(favorites)).toSingleDefault(Void.TYPE),
                            connectivityManager
                    ).ignoreElement();
                })
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable syncPlannedMeals(String uid) {
        LocalDate start = DateUtils.startOfCurrentWeek();
        LocalDate end = DateUtils.endOfNextWeek();

        return planLocalDataSource.getMealsForDateRange(start, end)
                .firstOrError()
                .flatMapCompletable(dayPlans -> {
                    if (dayPlans.isEmpty()) return Completable.complete();
                    return RxTask.withConnectivity(
                            syncDataSource.uploadPlans(uid, PlanMapper.groupByDay(dayPlans))
                                    .toSingleDefault(Void.TYPE),
                            connectivityManager
                    ).ignoreElement();
                })
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable syncAll(String uid) {
        return Completable.concatArray(
                syncFavorites(uid),
                syncPlannedMeals(uid)
        );
    }
}
