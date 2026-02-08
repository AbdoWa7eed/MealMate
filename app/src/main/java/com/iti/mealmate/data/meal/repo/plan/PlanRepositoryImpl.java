package com.iti.mealmate.data.meal.repo.plan;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.mapper.PlanMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlanRepositoryImpl implements PlanRepository{
    private final PlanLocalDataSourceImpl planLocalDataSource;
    private final MealLocalDataSource mealLocalDataSource;

    public PlanRepositoryImpl(PlanLocalDataSourceImpl planLocalDataSource, MealLocalDataSource mealLocalDataSource) {
        this.planLocalDataSource = planLocalDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public Completable addPlannedMeal(Meal meal, LocalDate date) {
        var mealEntity = PlanMapper.createPlannedEntity(meal.getId(), date);
        return mealLocalDataSource.isMealExists(meal.getId())
                .flatMapCompletable(isExists -> {
                    if (isExists) {
                        return planLocalDataSource.addMealToPlan(mealEntity);
                    } else {
                        return mealLocalDataSource
                                .insertMeal(MealMapper.domainToCachedEntity(meal, CacheType.NONE))
                                .andThen(planLocalDataSource.addMealToPlan(mealEntity));
                    }
                }).onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }


    @Override
    public Completable removePlannedMealFromDay(String mealId, LocalDate date) {
        return planLocalDataSource.removeMealFromDay(PlanMapper.createPlannedEntity(mealId, date))
                .onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Completable clearAllPlans() {
        return planLocalDataSource.clearAllPlans()
                .onErrorResumeNext(throwable ->
                        Completable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Flowable<List<PlannedMeal>> getPlannedMealsForDay(LocalDate date) {
        return planLocalDataSource
                .getMealsForDay(date)
                .map(PlanMapper::toDomainEntityList)
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Flowable<List<PlannedMeal>> getPlannedMealsForNextTwoWeeks() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.SATURDAY);
        LocalDate endDate = startOfWeek.plusWeeks(2);

        return planLocalDataSource
                .getMealsForDateRange(startOfWeek, endDate)
                .map(PlanMapper::toDomainEntityList)
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }
}
