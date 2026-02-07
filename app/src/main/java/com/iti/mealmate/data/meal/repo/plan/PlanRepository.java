package com.iti.mealmate.data.meal.repo.plan;

import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.model.entity.Meal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository {

    Completable addPlannedMeal(Meal meal, LocalDate date);

    Completable removePlannedMealFromDay(String mealId, LocalDate date);

    Completable clearAllPlans();

    Flowable<List<PlannedMeal>> getPlannedMealsForDay(LocalDate date);

    Flowable<List<PlannedMeal>> getPlannedMealsForNextTwoWeeks();
}
