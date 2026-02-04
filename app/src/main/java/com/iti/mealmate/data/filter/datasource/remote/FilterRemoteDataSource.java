package com.iti.mealmate.data.filter.datasource.remote;

import com.iti.mealmate.data.filter.model.response.CategoryResponse;
import com.iti.mealmate.data.filter.model.response.CountryResponse;
import com.iti.mealmate.data.filter.model.response.IngredientResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FilterRemoteDataSource {

    Single<List<CategoryResponse>> getCategories();

    Single<List<IngredientResponse>> getIngredients();

    Single<List<CountryResponse>> getCountries();
}



