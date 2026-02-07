package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "planned_meals",
        primaryKeys = {"mealId", "plannedDate"},
        indices = {
                @Index("mealId"),
                @Index("plannedDate"),
        },
        foreignKeys = {
                @ForeignKey(
                        entity = MealEntity.class,
                        parentColumns = "id",
                        childColumns = "mealId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class PlannedMealsEntity {

    @NonNull
    private  String mealId;

    private long plannedDate;

    public PlannedMealsEntity(@NonNull String mealId, long plannedDate) {
        this.mealId = mealId;
        this.plannedDate = plannedDate;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public long getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(long plannedDate) {
        this.plannedDate = plannedDate;
    }
}