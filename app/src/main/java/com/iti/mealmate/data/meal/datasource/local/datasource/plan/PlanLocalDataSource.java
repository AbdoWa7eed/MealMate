package com.iti.mealmate.data.meal.datasource.local.datasource.plan;

import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.util.List;

public interface PlanLocalDataSource {

    Completable addMealToPlan(PlannedMealEntity plannedMeal);

    Completable removeMealFromDay(PlannedMealEntity plannedMeal);

    Completable clearAllPlans();

    Flowable<List<PlannedMealDetailEntity>> getMealsForDay(LocalDate date);

    Flowable<List<PlannedMealDetailEntity>> getMealsForDateRange(LocalDate startDate, LocalDate endDate);
}
