package com.iti.mealmate.data.meal.model.response;

import androidx.annotation.NonNull;

public class MealOfTheDay {
    private final String  mealId;
    private final String date;

    public MealOfTheDay(String mealId, @NonNull String date) {
        this.mealId = mealId;
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

}
