package com.iti.mealmate.data.meal.model.response;

import androidx.annotation.NonNull;

import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;

public class MealOfTheDay {
    private Meal dailyMeal;
    private String date;
    private List<Meal> suggestedMeals;

    public MealOfTheDay() {
    }

    public MealOfTheDay(Meal dailyMeal, @NonNull String date, List<Meal> trendingMeals) {
        this.dailyMeal = dailyMeal;
        this.date = date;
        this.suggestedMeals = trendingMeals;
    }

    public Meal getDailyMeal() {
        return dailyMeal;
    }

    public void setDailyMeal(Meal dailyMeal) {
        this.dailyMeal = dailyMeal;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public List<Meal> getSuggestedMeals() {
        return suggestedMeals;
    }

    public void setSuggestedMeals(List<Meal> suggestedMeals) {
        this.suggestedMeals = suggestedMeals;
    }

}
