package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class PlannedMealDetailEntity implements Serializable {

    @Embedded
    private PlannedMealEntity plannedMeal;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "id"
    )
    private MealEntity meal;


    public PlannedMealEntity getPlannedMeal() {
        return plannedMeal;
    }

    public void setPlannedMeal(PlannedMealEntity plannedMeal) {
        this.plannedMeal = plannedMeal;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }
}
