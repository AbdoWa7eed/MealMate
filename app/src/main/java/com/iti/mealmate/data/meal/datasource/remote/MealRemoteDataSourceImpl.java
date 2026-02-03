package com.iti.mealmate.data.meal.datasource.remote;

import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.exceptions.MealNotFoundException;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {

    private final MealApiService mealApiService;

    private final FirestoreMealHelper firestoreMealHelper;

    public MealRemoteDataSourceImpl(MealApiService mealApiService, FirestoreMealHelper firestoreMealHelper) {
        this.mealApiService = mealApiService;
        this.firestoreMealHelper = firestoreMealHelper;
    }

    @Override
    public Single<Meal> getMealOfTheDay() {
        return firestoreMealHelper.getMealOfTheDay()
                .flatMap(mealOfTheDay -> getMealById(mealOfTheDay.getMealId())
                                .onErrorResumeNext(error -> refreshDailyMeal()))
                .onErrorResumeNext(error -> {
                    if (error instanceof MealNotFoundException) {
                        return refreshDailyMeal();
                    }
                    return Single.error(error);
                });
    }

    private Single<Meal> refreshDailyMeal() {
        return mealApiService.getRandomMeal()
                .flatMap(mealsBaseResponse -> {
                    Meal meal = MealMapper.toEntity(mealsBaseResponse.getMeals().get(0));
                    return firestoreMealHelper.saveMealOfTheDay(meal.getId()).map(aVoid -> meal);
                });
    }


    @Override
    public Single<List<Meal>> getMealsByIngredient(String ingredient) {
        return mealApiService.getMealsByIngredient(ingredient)
                .map(mealsBaseResponse -> mealsBaseResponse
                        .getMeals().stream()
                        .map(MealMapper::toEntity)
                        .collect(Collectors.toList()));
    }

    @Override
    public Single<Meal> getMealById(String id) {
        return mealApiService.getMealById(id)
                .map(mealsBaseResponse -> MealMapper.toEntity(mealsBaseResponse.getMeals().get(0)));
    }

    @Override
    public Single<List<Meal>> searchMealsByName(String name) {
        return mealApiService.searchMealsByName(name)
                .map(mealsBaseResponse -> mealsBaseResponse
                        .getMeals().stream()
                        .map(MealMapper::toEntity)
                        .collect(Collectors.toList()));
    }
}
