package com.iti.mealmate.data.meal.model.response;

import androidx.annotation.NonNull;

public class MealOfTheDay {
    private String mealId;
    private String date;

    public MealOfTheDay() {
    }

    public MealOfTheDay(String mealId, @NonNull String date) {
        this.mealId = mealId;
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

}
