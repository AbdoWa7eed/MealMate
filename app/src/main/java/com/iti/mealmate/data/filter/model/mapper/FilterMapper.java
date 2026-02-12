package com.iti.mealmate.data.filter.model.mapper;

import com.iti.mealmate.data.filter.model.entity.FilterItem;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.data.filter.model.response.CategoryResponse;
import com.iti.mealmate.data.filter.model.response.CountryResponse;
import com.iti.mealmate.data.filter.model.response.IngredientResponse;
import com.iti.mealmate.data.filter.util.CountryFlagProvider;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterMapper {

    private FilterMapper() {}

    public static FilterItem fromCategory(CategoryResponse response) {
        if (response == null) return null;
        return new FilterItem(
                response.getIdCategory(),
                response.getStrCategory(),
                response.getStrCategoryThumb(),
                FilterType.CATEGORY
        );
    }

    public static List<FilterItem> fromCategoryList(List<CategoryResponse> responses) {
        if (responses == null) {
            return Collections.emptyList();
        }
        return responses.stream()
                .map(FilterMapper::fromCategory)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static FilterItem fromIngredient(IngredientResponse response) {
        if (response == null) return null;
        return new FilterItem(
                response.getIdIngredient(),
                response.getStrIngredient(),
                response.getStrThumb(),
                FilterType.INGREDIENT
        );
    }

    public static List<FilterItem> fromIngredientList(List<IngredientResponse> responses) {
        if (responses == null) {
            return Collections.emptyList();
        }
        return responses.stream()
                .map(FilterMapper::fromIngredient)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static FilterItem fromCountry(CountryResponse response) {
        if (response == null) return null;
        String area = response.getStrArea();
        return new FilterItem(
                area,
                area,
                CountryFlagProvider.getFlagUrl(area),
                FilterType.COUNTRY
        );
    }

    public static List<FilterItem> fromCountryList(List<CountryResponse> responses) {
        if (responses == null) {
            return Collections.emptyList();
        }
        return responses.stream()
                .map(FilterMapper::fromCountry)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

