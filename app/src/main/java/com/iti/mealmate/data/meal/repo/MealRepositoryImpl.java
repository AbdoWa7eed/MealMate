package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

class MealRepositoryImpl implements MealRepository {

    private final MealRemoteDataSource remoteDataSource;

    public MealRepositoryImpl(MealRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<Meal> getMealOfTheDay() {
        return remoteDataSource.getMealOfTheDay();
    }

    @Override
    public Single<List<Meal>> getMealsByIngredient(String ingredient) {
        return remoteDataSource.getMealsByIngredient(ingredient);
    }

    @Override
    public Single<List<Meal>> getSuggestedMeals() {
        return remoteDataSource.getSuggestedMeals();
    }

    @Override
    public Single<List<Meal>> searchMealsByName(String name) {
        return remoteDataSource.searchMealsByName(name);
    }
}
