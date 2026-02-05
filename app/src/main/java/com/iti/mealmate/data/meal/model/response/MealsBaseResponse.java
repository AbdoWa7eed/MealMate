package com.iti.mealmate.data.meal.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MealsBaseResponse {
    @SerializedName("meals")
    private List<MealResponse> meals;

    public MealsBaseResponse(List<MealResponse> meals) {
        this.meals = meals;
    }

    public List<MealResponse> getMeals() {
        if(meals == null)
            return new ArrayList<>();
        return meals;
    }

    public void setMeals(List<MealResponse> meals) {
        this.meals = meals;
    }

}
