package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "planned_meals",
        primaryKeys = {"mealId", "weekStartDate", "dayName"},
        indices = {
                @Index("mealId"),
                @Index("weekStartDate"),
                @Index(value = {"weekStartDate", "dayName"})
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
    public String mealId;

    @NonNull
    public String dayName;

    @NonNull
    public String weekStartDate;

    public Long plannedDate;

    public PlannedMealsEntity(@NonNull String mealId, @NonNull String dayName,
                              @NonNull String weekStartDate, Long plannedDate) {
        this.mealId = mealId;
        this.dayName = dayName;
        this.weekStartDate = weekStartDate;
        this.plannedDate = plannedDate;
    }
}