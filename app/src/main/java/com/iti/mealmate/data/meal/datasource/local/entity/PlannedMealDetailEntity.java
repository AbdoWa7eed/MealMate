package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class PlannedMealDetailEntity implements Serializable {

    @Embedded
    private PlannedMealsEntity plannedMeal;

    @Relation(
            parentColumn = "mealId",
            entityColumn = "id"
    )
    private MealEntity meal;


    public PlannedMealsEntity getPlannedMeal() {
        return plannedMeal;
    }

    public void setPlannedMeal(PlannedMealsEntity plannedMeal) {
        this.plannedMeal = plannedMeal;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }
}
