package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import com.iti.mealmate.data.auth.datasource.local.entity.UserEntity;

@Entity(
    tableName = "user_plan",
    indices = {
        @Index(value = {"userId", "mealId", "weekStartDate", "dayName"}, unique = true),
        @Index("userId"),
        @Index("weekStartDate")
    },
    foreignKeys = {
        @ForeignKey(entity = UserEntity.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = MealEntity.class, parentColumns = "id", childColumns = "mealId", onDelete = ForeignKey.CASCADE)
    }
)
public class UserPlanEntity {

    @NonNull
    public String userId;

    @NonNull
    public String mealId;

    @NonNull
    public String dayName;

    @NonNull
    public String weekStartDate;

}
