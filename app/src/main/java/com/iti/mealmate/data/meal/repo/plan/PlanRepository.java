package com.iti.mealmate.data.meal.repo.plan;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

public interface PlanRepository {

    Completable addPlannedMeal(PlannedMeal meal);

    Completable removePlannedMealFromDay(PlannedMeal meal);

    Flowable<List<DayPlan>> getPlannedMealsForNextTwoWeeks(String uid);

    void resetFetchFlag();
}
