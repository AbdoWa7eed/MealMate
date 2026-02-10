package com.iti.mealmate.data.meal.model.entity;

import com.google.firebase.firestore.Exclude;

import java.time.LocalDate;

public class PlannedMeal {
    private Meal meal;
    private String plannedDate;

    public PlannedMeal() {
    }

    public PlannedMeal(Meal meal, LocalDate plannedDate) {
        this.meal = meal;
        this.plannedDate = plannedDate.toString();
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    @Exclude
    public LocalDate getPlannedLocalDate() {
        return plannedDate != null ? LocalDate.parse(plannedDate) : null;
    }

    public void setPlannedLocalDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate != null ? plannedDate.toString() : null;
    }
}