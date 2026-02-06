package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import com.iti.mealmate.data.auth.datasource.local.entity.UserEntity;

@Entity(
    tableName = "user_favorites",
    primaryKeys = {"userId", "mealId"},
    foreignKeys = {
        @ForeignKey(entity = UserEntity.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = MealEntity.class, parentColumns = "id", childColumns = "mealId", onDelete = ForeignKey.CASCADE)
    },
    indices = {@Index("mealId")}
)
public class UserFavoriteEntity {

    @NonNull
    public String userId;

    @NonNull
    public String mealId;
}
