package com.iti.mealmate.data.meal.repo.plan;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository {

    Completable addPlannedMeal(PlannedMeal meal);

    Completable removePlannedMealFromDay(PlannedMeal meal);

    Completable clearAllPlans();

    Flowable<DayPlan> getPlannedMealsForDay(LocalDate date);

    Flowable<List<DayPlan>> getPlannedMealsForNextTwoWeeks();
}
