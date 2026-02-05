package com.iti.mealmate.data.meal.datasource.remote;


import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.response.MealResponse;

import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface MealRemoteDataSource {
    Single<Meal> getMealOfTheDay();

    Single<List<MealResponse>> getMealsByIngredient(String ingredient);

    Single<List<MealResponse>> searchMealsByName(String name);

    Single<List<Meal>> getSuggestedMeals();

    Single<List<MealResponse>> getMealsByCategory(String category);


    Single<List<MealResponse>> getMealsByCountry(String country);
}