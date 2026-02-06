package com.iti.mealmate.data.meal.datasource.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.UserFavoriteEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.UserPlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Query("SELECT * FROM meals WHERE id = :id")
    Flowable<MealEntity> getMealById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavorite(UserFavoriteEntity favorite);

    @Delete
    Completable deleteFavorite(UserFavoriteEntity favorite);

    @Query("SELECT meals.* FROM meals JOIN user_favorites ON meals.id = user_favorites.mealId WHERE user_favorites.userId = :userId")
    Flowable<List<MealEntity>> getFavoriteMeals(String userId);

    @Query("SELECT EXISTS(SELECT 1 FROM user_favorites WHERE userId = :userId AND mealId = :mealId)")
    Flowable<Boolean> isFavorite(String userId, String mealId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlan(UserPlanEntity plan);

    @Delete
    Completable deletePlan(UserPlanEntity plan);

    @Query("SELECT meals.*, user_plan.dayName, user_plan.weekStartDate FROM meals " +
           "INNER JOIN user_plan ON meals.id = user_plan.mealId " +
           "WHERE user_plan.userId = :userId AND user_plan.weekStartDate = :weekStartDate")
    Flowable<List<MealEntity>> getPlannedMeals(String userId, String weekStartDate);
}
