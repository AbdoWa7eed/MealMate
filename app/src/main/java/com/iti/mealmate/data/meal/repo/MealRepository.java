package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealRepository {

    Single<Meal> getMealOfTheDay();

    Single<List<Meal>> getMealsByIngredient(String ingredient);

    Single<Meal> getMealById(String id);

    Single<List<Meal>> searchMealsByName(String name);
}
