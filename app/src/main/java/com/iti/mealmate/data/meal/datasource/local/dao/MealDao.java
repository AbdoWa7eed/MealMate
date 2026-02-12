package com.iti.mealmate.data.meal.datasource.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<MealEntity> meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Query("SELECT * FROM meals WHERE id = :mealId LIMIT 1")
    Flowable<MealEntity> getMealById(String mealId);

    @Query("SELECT * FROM meals WHERE cacheType = :type ORDER BY cachedDate DESC")
    Flowable<List<MealEntity>> getMealsByType(CacheType type);

    @Query("SELECT * FROM meals WHERE cacheType = :type AND cachedDate = :todayStart ORDER BY id DESC")
    Flowable<List<MealEntity>> getTodayMeals(CacheType type, long todayStart);

    @Query("SELECT * FROM meals WHERE isFavorite = 1")
    Flowable<List<MealEntity>> getFavorites();

    @Query("UPDATE meals SET isFavorite = 1 WHERE id = :mealId")
    Completable addToFavorites(String mealId);

    @Query("UPDATE meals SET isFavorite = 0 WHERE id = :mealId")
    Completable removeFromFavorites(String mealId);

    @Query("SELECT isFavorite FROM meals WHERE id = :mealId")
    Single<Boolean> isFavorite(String mealId);

    @Query("SELECT EXISTS(SELECT 1 FROM meals WHERE id = :mealId)")
    Single<Boolean> isMealExists(String mealId);

    @Query("DELETE FROM meals")
    Completable deleteAllMeals();
}
