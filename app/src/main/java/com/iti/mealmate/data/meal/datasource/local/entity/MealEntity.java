package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.data.meal.datasource.local.converter.MealIngredientConverter;
import java.util.List;

@Entity(tableName = "meals")
public class MealEntity {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;
    public String category;
    public String area;
    public String instructions;
    public String thumbnailUrl;
    public String youtubeUrl;
    public String sourceUrl;

    @TypeConverters(MealIngredientConverter.class)
    public List<MealIngredient> ingredients;
}
