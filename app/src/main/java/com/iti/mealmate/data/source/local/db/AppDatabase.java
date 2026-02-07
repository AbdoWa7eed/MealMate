package com.iti.mealmate.data.source.local.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.iti.mealmate.data.meal.datasource.local.converter.CacheTypeConverter;
import com.iti.mealmate.data.meal.datasource.local.dao.MealDao;
import com.iti.mealmate.data.meal.datasource.local.dao.PlanDao;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealsEntity;
import com.iti.mealmate.data.meal.datasource.local.converter.MealIngredientConverter;

@Database(entities = {
    MealEntity.class,
    PlannedMealsEntity.class
}, version = 1, exportSchema = false)
@TypeConverters({MealIngredientConverter.class, CacheTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "mealmate_db";

    public abstract MealDao mealDao();
    public abstract PlanDao planDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
