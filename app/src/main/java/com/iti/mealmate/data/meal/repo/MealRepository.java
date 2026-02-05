package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealLight;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealRepository {

    Single<Meal> getMealOfTheDay();

    Single<List<MealLight>> getMealsByIngredient(String ingredient);

    Single<List<Meal>>  getSuggestedMeals();

    Single<List<MealLight>> searchMealsByName(String name);

    Single<List<MealLight>> getMealsByCategory(String category);

    Single<List<MealLight>> getMealsByCountry(String country);
}
