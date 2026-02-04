package com.iti.mealmate.data.filter.api;

import com.iti.mealmate.data.filter.model.response.CategoriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.CountriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.IngredientsBaseResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface FilterApiService {

    @GET("categories.php")
    Single<CategoriesBaseResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientsBaseResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountriesBaseResponse> getCountries();
}


