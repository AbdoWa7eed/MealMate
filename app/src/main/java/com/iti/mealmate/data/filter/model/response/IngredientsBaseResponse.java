package com.iti.mealmate.data.filter.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsBaseResponse {

    @SerializedName("meals")
    private List<IngredientResponse> meals;

    public IngredientsBaseResponse(List<IngredientResponse> meals) {
        this.meals = meals;
    }

    public List<IngredientResponse> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientResponse> meals) {
        this.meals = meals;
    }
}


