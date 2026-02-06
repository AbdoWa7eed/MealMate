package com.iti.mealmate.data.auth.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.iti.mealmate.data.auth.model.AuthProvider;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    public String userId;

    public String name;
}