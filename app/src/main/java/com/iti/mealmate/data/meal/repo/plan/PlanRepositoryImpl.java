package com.iti.mealmate.data.meal.repo.plan;

import android.util.Log;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.remote.sync.UserMealSyncDataSource;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.mapper.PlanMapper;

import java.time.LocalDate;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlanRepositoryImpl implements PlanRepository {
    private final PlanLocalDataSource planLocalDataSource;
    private final MealLocalDataSource mealLocalDataSource;
    private final UserMealSyncDataSource syncDataSource;

    private final AppConnectivityManager connectivityManager;


    public PlanRepositoryImpl(PlanLocalDataSourceImpl planLocalDataSource,
                              MealLocalDataSource mealLocalDataSource,
                              UserMealSyncDataSource syncDataSource,
                              AppConnectivityManager connectivityManager) {
        this.planLocalDataSource = planLocalDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        this.syncDataSource = syncDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Completable addPlannedMeal(PlannedMeal plannedMeal) {
        var plannedEntity = PlanMapper.createPlannedEntity(plannedMeal);
        return mealLocalDataSource
                .isMealExists(plannedMeal.getMeal().getId())
                .flatMapCompletable(isExists -> {
                    if (isExists) {
                        return planLocalDataSource.addMealToPlan(plannedEntity);
                    } else {
                        var mealEntity = MealMapper
                                .domainToCachedEntity(plannedMeal.getMeal(), CacheType.NONE);
                        return mealLocalDataSource
                                .insertMeal(mealEntity)
                                .andThen(planLocalDataSource.addMealToPlan(plannedEntity));
                    }
                }).onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }


    @Override
    public Completable removePlannedMealFromDay(PlannedMeal plannedMeal) {
        return planLocalDataSource.removeMealFromDay(PlanMapper.createPlannedEntity(plannedMeal))
                .onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }



    @Override
    public Flowable<List<DayPlan>> getPlannedMealsForNextTwoWeeks(String uid) {
        LocalDate start = DateUtils.startOfCurrentWeek();
        LocalDate end = DateUtils.endOfNextWeek();

        return getLocalPlans(start, end)
                .take(1)
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        return RxTask.withConnectivity(
                                        syncDataSource.downloadPlans(uid),
                                        connectivityManager
                                )
                                .flatMapCompletable(this::cacheRemotePlans)
                                .andThen(getLocalPlans(start, end))
                                .onErrorResumeNext(Flowable::error);
                    } else {
                        return getLocalPlans(start, end);
                    }
                })
                .onErrorResumeNext(throwable ->
                        Flowable.error(AppErrorHandler.handle(throwable)));
    }

    private Flowable<List<DayPlan>> getLocalPlans(LocalDate start, LocalDate end) {
        return planLocalDataSource
                .getMealsForDateRange(start, end)
                .map(PlanMapper::groupByDay);
    }

    private Completable cacheRemotePlans(List<DayPlan> remotePlans) {
        return Flowable.fromIterable(remotePlans)
                .flatMapIterable(DayPlan::getMeals)
                .concatMapCompletable(this::addPlannedMeal);
    }


}
