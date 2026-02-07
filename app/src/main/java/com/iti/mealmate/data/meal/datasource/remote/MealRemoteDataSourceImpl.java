package com.iti.mealmate.data.meal.datasource.remote;

import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;
import com.iti.mealmate.data.meal.model.response.MealOfTheDay;
import com.iti.mealmate.data.meal.model.response.MealResponse;
import com.iti.mealmate.data.meal.model.response.MealsBaseResponse;
import java.util.List;
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

    private static final String TAG = "MEALDATASOURCE";

    @Override
    public Single<Meal> getMealOfTheDay() {

        return firestoreMealHelper.getMealOfTheDay()
                .map(MealOfTheDay::getDailyMeal)
                .onErrorResumeNext(e -> getOrFetchDailyMeals().map(MealOfTheDay::getDailyMeal));

    }

    @Override
    public Single<List<Meal>> getSuggestedMeals() {
        return firestoreMealHelper.getMealOfTheDay()
                .map(MealOfTheDay::getSuggestedMeals)
                .onErrorResumeNext(e -> getOrFetchDailyMeals()
                        .map(MealOfTheDay::getSuggestedMeals)
                );

    }

    @Override
    public Single<List<MealResponse>> getMealsByCategory(String category) {
        return mealApiService
                .getMealsByCategory(category).map(MealsBaseResponse::getMeals);
    }

    @Override
    public Single<List<MealResponse>> getMealsByCountry(String country) {
        return mealApiService
                .getMealsByCountry(country).map(MealsBaseResponse::getMeals);
    }

    @Override
    public Single<List<MealResponse>> getMealsByIngredient(String ingredient) {
        return mealApiService.getMealsByIngredient(ingredient)
                .map(MealsBaseResponse::getMeals);
    }

    @Override
    public Single<List<MealResponse>> searchMealsByName(String name) {
        return mealApiService.searchMealsByName(name)
                .map(MealsBaseResponse::getMeals);
    }

    @Override
    public Single<MealResponse> getMealById(String id) {
        return mealApiService.getMealById(id).map(response -> response.getMeals().get(0));
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
                .flatMap(i -> fetchRandomMeal().toFlowable())
                .toList()
                .flatMap(meals -> {
                    if (meals.isEmpty()) {
                        return fetchSuggestedMeals();
                    }
                    return Single.just(meals);
                });
    }

}