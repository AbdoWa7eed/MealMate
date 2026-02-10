package com.iti.mealmate.data.meal.model.entity;

import com.google.firebase.firestore.Exclude;

import java.time.LocalDate;
import java.util.List;
public class DayPlan {

    private String date;
    private List<PlannedMeal> meals;

    public DayPlan() {}

    public DayPlan(LocalDate date, List<PlannedMeal> meals) {
        this.date = date.toString();
        this.meals = meals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Exclude
    public LocalDate getLocalDate() {
        return date != null ? LocalDate.parse(date) : null;
    }

    public void setLocalDate(LocalDate date) {
        this.date = date != null ? date.toString() : null;
    }

    public List<PlannedMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<PlannedMeal> meals) {
        this.meals = meals;
    }


    @Exclude
    public boolean isToday() {
        return getLocalDate().equals(LocalDate.now());
    }
}