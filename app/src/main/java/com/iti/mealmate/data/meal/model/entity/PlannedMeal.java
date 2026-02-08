package com.iti.mealmate.data.meal.model.entity;

import java.time.LocalDate;

public class PlannedMeal {
    private Meal meal;
    private LocalDate plannedDate;

    public PlannedMeal(Meal meal, LocalDate plannedDate) {
        this.meal = meal;
        this.plannedDate = plannedDate;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }
}