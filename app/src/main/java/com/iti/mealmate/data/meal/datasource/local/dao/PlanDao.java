package com.iti.mealmate.data.meal.datasource.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealsEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

@Dao
public interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToPlan(PlannedMealsEntity plan);

    @Query("DELETE FROM planned_meals WHERE mealId = :mealId AND plannedDate = :plannedDate")
    Completable removeMealFromDay(String mealId, long plannedDate);

    @Query("DELETE FROM planned_meals")
    Completable clearAllPlans();

    @Transaction
    @Query("SELECT * FROM planned_meals WHERE plannedDate = :plannedDate")
    Flowable<List<PlannedMealDetailEntity>> getPlannedMealsWithDetails(long plannedDate);

    @Transaction
    @Query("SELECT * FROM planned_meals WHERE plannedDate BETWEEN :startDate AND :endDate")
    Flowable<List<PlannedMealDetailEntity>> getPlannedMealsWithDetailsForRange(long startDate, long endDate);
}
