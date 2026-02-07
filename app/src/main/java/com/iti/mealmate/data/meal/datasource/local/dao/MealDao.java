package com.iti.mealmate.data.meal.datasource.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<MealEntity> meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealEntity meals);
    @Query("SELECT * FROM meals WHERE id = :mealId LIMIT 1")
    LiveData<MealEntity> getMealById(String mealId);

    @Query("SELECT * FROM meals WHERE cacheType = :type ORDER BY cachedDate DESC")
    LiveData<List<MealEntity>> getMealsByType(CacheType type);

    @Query("SELECT * FROM meals WHERE isFavorite = 1")
    LiveData<List<MealEntity>> getFavorites();

    @Query("UPDATE meals SET isFavorite = 1 WHERE id = :mealId")
    void addToFavorites(String mealId);

    @Query("UPDATE meals SET isFavorite = 0 WHERE id = :mealId")
    void removeFromFavorites(String mealId);

    @Query("SELECT isFavorite FROM  meals WHERE id = :mealId")
    boolean isFavorite(String mealId);

    @Query("DELETE FROM meals")
    int deleteAllMeals();
}