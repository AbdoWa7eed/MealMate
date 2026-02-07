package com.iti.mealmate.data.meal.datasource.local.datasource.plan;

import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.model.entity.Meal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.util.List;

public interface PlanLocalDataSource {

    Completable addMealToPlan(Meal meal, LocalDate date);

    Completable removeMealFromDay(String mealId, LocalDate date);

    Completable clearAllPlans();

    Flowable<List<PlannedMealDetailEntity>> getMealsForDay(LocalDate date);

    Flowable<List<PlannedMealDetailEntity>> getMealsForDateRange(LocalDate startDate, LocalDate endDate);
}
