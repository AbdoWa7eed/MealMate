package com.iti.mealmate.data.meal.datasource.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
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

    public List<MealIngredient> ingredients;
    public boolean isFavorite;

    @NonNull
    public CacheType cacheType;
    public Long cachedDate;

    public MealEntity(@NonNull String id,
                      String name,
                      String category,
                      String area,
                      String instructions,
                      String thumbnailUrl,
                      String youtubeUrl,
                      String sourceUrl,
                      List<MealIngredient> ingredients,
                      boolean isFavorite,
                      @NonNull CacheType cacheType,
                      Long cachedDate) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbnailUrl = thumbnailUrl;
        this.youtubeUrl = youtubeUrl;
        this.sourceUrl = sourceUrl;
        this.ingredients = ingredients;
        this.isFavorite = isFavorite;
        this.cacheType = cacheType;
        this.cachedDate = cachedDate;
    }
}