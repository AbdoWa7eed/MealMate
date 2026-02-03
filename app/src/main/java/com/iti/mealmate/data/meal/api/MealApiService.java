package com.iti.mealmate.data.meal.api;


import com.iti.mealmate.data.meal.model.response.MealsBaseResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("random.php")
    Single<MealsBaseResponse> getRandomMeal();

    @GET("filter.php")
    Single<MealsBaseResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<MealsBaseResponse> getMealById(@Query("i") String id);

    @GET("search.php")
    Single<MealsBaseResponse> searchMealsByName(@Query("s") String name);

}
