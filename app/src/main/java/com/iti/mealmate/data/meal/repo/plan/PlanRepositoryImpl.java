package com.iti.mealmate.data.meal.repo.plan;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.mapper.PlanMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlanRepositoryImpl implements PlanRepository {
    private final PlanLocalDataSource planLocalDataSource;
    private final MealLocalDataSource mealLocalDataSource;

    public PlanRepositoryImpl(PlanLocalDataSourceImpl planLocalDataSource, MealLocalDataSource mealLocalDataSource) {
        this.planLocalDataSource = planLocalDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
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
    public Completable clearAllPlans() {
        return planLocalDataSource.clearAllPlans()
                .onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }

    public Flowable<DayPlan> getPlannedMealsForDay(LocalDate date) {
        return planLocalDataSource
                .getMealsForDay(date)
                .map(meals -> PlanMapper.groupByDay(meals).get(0))
                .onErrorResumeNext(throwable ->
                        Flowable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Flowable<List<DayPlan>> getPlannedMealsForNextTwoWeeks() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.SATURDAY);
        LocalDate endDate = startOfWeek.plusWeeks(2);

        return planLocalDataSource
                .getMealsForDateRange(startOfWeek, endDate)
                .map(PlanMapper::groupByDay)
                .onErrorResumeNext(throwable ->
                        Flowable.error(AppErrorHandler.handle(throwable)));
    }
}
