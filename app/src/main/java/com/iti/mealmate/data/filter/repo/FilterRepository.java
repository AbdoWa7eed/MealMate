package com.iti.mealmate.data.filter.repo;

import com.iti.mealmate.data.filter.model.entity.FilterItem;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FilterRepository {

    Single<List<FilterItem>> getCategoryFilters();

    Single<List<FilterItem>> getIngredientFilters();

    Single<List<FilterItem>> getCountryFilters();
}


