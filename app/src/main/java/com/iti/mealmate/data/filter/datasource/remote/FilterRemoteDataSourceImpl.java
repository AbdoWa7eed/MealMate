package com.iti.mealmate.data.filter.datasource.remote;

import com.iti.mealmate.data.filter.api.FilterApiService;
import com.iti.mealmate.data.filter.model.response.CategoriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.CountriesBaseResponse;
import com.iti.mealmate.data.filter.model.response.IngredientsBaseResponse;
import com.iti.mealmate.data.filter.model.response.CategoryResponse;
import com.iti.mealmate.data.filter.model.response.CountryResponse;
import com.iti.mealmate.data.filter.model.response.IngredientResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FilterRemoteDataSourceImpl implements FilterRemoteDataSource {

    private final FilterApiService apiService;

    public FilterRemoteDataSourceImpl(FilterApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<List<CategoryResponse>> getCategories() {
        return apiService.getCategories()
                .map(CategoriesBaseResponse::getCategories);
    }

    @Override
    public Single<List<IngredientResponse>> getIngredients() {
        return apiService.getIngredients()
                .map(IngredientsBaseResponse::getMeals);
    }

    @Override
    public Single<List<CountryResponse>> getCountries() {
        return apiService.getCountries()
                .map(CountriesBaseResponse::getMeals);
    }
}



