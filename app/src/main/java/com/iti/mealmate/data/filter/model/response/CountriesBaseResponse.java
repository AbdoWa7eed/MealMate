package com.iti.mealmate.data.filter.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesBaseResponse {

    @SerializedName("meals")
    private List<CountryResponse> meals;

    public CountriesBaseResponse(List<CountryResponse> meals) {
        this.meals = meals;
    }

    public List<CountryResponse> getMeals() {
        return meals;
    }

    public void setMeals(List<CountryResponse> meals) {
        this.meals = meals;
    }
}


