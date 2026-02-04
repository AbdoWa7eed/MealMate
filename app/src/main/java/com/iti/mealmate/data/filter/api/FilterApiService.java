package com.iti.mealmate.data.filter.api;

import com.iti.mealmate.data.filter.model.response.CategoriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.CountriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.IngredientsBaseResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterApiService {

    @GET("categories.php")
    Single<CategoriesBaseResponse> getCategories();

    @GET("list.php")
    Single<IngredientsBaseResponse> getIngredients(@Query("i") String list);

    @GET("list.php")
    Single<CountriesBaseResponse> getCountries(@Query("a") String list);
}


