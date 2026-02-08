package com.iti.mealmate.data.meal.model.entity;

import java.time.LocalDate;
import java.util.List;

public class DayPlan {

    private LocalDate date;
    private List<PlannedMeal> meals;
    public DayPlan(LocalDate date, List<PlannedMeal> meals) {
        this.date = date;
        this.meals = meals;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<PlannedMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<PlannedMeal> meals) {
        this.meals = meals;
    }

    public boolean isToday() {
        return date.equals(LocalDate.now());
    }

}
