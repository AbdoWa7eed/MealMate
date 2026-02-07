package com.iti.mealmate.data.meal.datasource.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealsEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import java.util.List;

@Dao
public interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToPlan(PlannedMealsEntity plan);

    @Transaction
    @Query("SELECT m.* FROM meals m " +
            "INNER JOIN planned_meals p ON m.id = p.mealId " +
            "WHERE p.weekStartDate = :weekStart " +
            "ORDER BY CASE p.dayName " +
            "WHEN 'MONDAY' THEN 1 " +
            "WHEN 'TUESDAY' THEN 2 " +
            "WHEN 'WEDNESDAY' THEN 3 " +
            "WHEN 'THURSDAY' THEN 4 " +
            "WHEN 'FRIDAY' THEN 5 " +
            "WHEN 'SATURDAY' THEN 6 " +
            "WHEN 'SUNDAY' THEN 7 END")
    Flowable<List<MealEntity>> getPlannedMealsForWeek(String weekStart);

    @Query("DELETE FROM planned_meals WHERE weekStartDate = :weekStart AND dayName = :dayName")
    Completable removeFromPlan(String weekStart, String dayName);

    @Query("DELETE FROM planned_meals")
    Completable clearAllPlans();
}
