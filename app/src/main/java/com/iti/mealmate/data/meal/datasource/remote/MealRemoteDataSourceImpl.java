package com.iti.mealmate.data.meal.datasource.remote;

import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.response.MealOfTheDay;
import com.iti.mealmate.data.meal.model.response.MealsBaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource {

    private final MealApiService mealApiService;
    private final FirestoreMealHelper firestoreMealHelper;
    private Single<MealOfTheDay> cachedFetchOperation;

    public MealRemoteDataSourceImpl(MealApiService mealApiService, FirestoreMealHelper firestoreMealHelper) {
        this.mealApiService = mealApiService;
        this.firestoreMealHelper = firestoreMealHelper;
    }

    @Override
    public Single<Meal> getMealOfTheDay() {
        return  firestoreMealHelper.getMealOfTheDay()
                .map(MealOfTheDay::getDailyMeal)
                .onErrorResumeNext(e -> getOrFetchDailyMeals()
                        .map(MealOfTheDay::getDailyMeal)
                );
    }

    @Override
    public Single<List<Meal>> getSuggestedMeals() {
        return  firestoreMealHelper.getMealOfTheDay()
                .map(MealOfTheDay::getSuggestedMeals)
                .onErrorResumeNext(e -> getOrFetchDailyMeals()
                        .map(MealOfTheDay::getSuggestedMeals)
                );
    }

    @Override
    public Single<List<Meal>> getMealsByIngredient(String ingredient) {
        return mealApiService.getMealsByIngredient(ingredient)
                .map(this::mapResponseToMealList);
    }

    @Override
    public Single<List<Meal>> searchMealsByName(String name) {
        return mealApiService.searchMealsByName(name)
                .map(this::mapResponseToMealList);
    }



    private Single<MealOfTheDay> getOrFetchDailyMeals() {
        if (cachedFetchOperation == null) {
            cachedFetchOperation = fetchAndCacheDailyMeals().cache();
        }
        return cachedFetchOperation;
    }

    private Single<MealOfTheDay> fetchAndCacheDailyMeals() {
        return Single.zip(
                fetchRandomMeal(),
                fetchSuggestedMeals(),
                (dailyMeal, suggestedMeals) -> {
                    MealOfTheDay mealOfTheDay = new MealOfTheDay();
                    mealOfTheDay.setDailyMeal(dailyMeal);
                    mealOfTheDay.setSuggestedMeals(suggestedMeals);
                    return mealOfTheDay;
                }
        ).flatMap(this::saveDailyMealsQuietly);
    }

    private Single<MealOfTheDay> saveDailyMealsQuietly(MealOfTheDay mealOfTheDay) {
        return firestoreMealHelper.saveMealOfTheDay(mealOfTheDay)
                .onErrorReturnItem(mealOfTheDay)
                .doFinally(() -> cachedFetchOperation = null);
    }


    private Single<Meal> fetchRandomMeal() {
        return mealApiService.getRandomMeal()
                .map(response -> MealMapper.toEntity(response.getMeals().get(0)));
    }

    private Single<List<Meal>> fetchSuggestedMeals() {
        return Flowable.range(0, 10)
                .flatMap(i ->
                        mealApiService.getRandomMeal()
                                .map(response -> MealMapper.toEntity(response.getMeals().get(0)))
                                .toFlowable()
                )
                .toList()
                .flatMap(meals -> {
                    if (meals.isEmpty()) {
                        return fetchSuggestedMeals();
                    }
                    return Single.just(meals);
                });
    }

    private List<Meal> mapResponseToMealList(MealsBaseResponse response) {
        if (response.getMeals() == null) {
            return new ArrayList<>();
        }
        return response.getMeals().stream()
                .map(MealMapper::toEntity)
                .collect(Collectors.toList());
    }
}