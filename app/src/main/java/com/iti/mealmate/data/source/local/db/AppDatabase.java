package com.iti.mealmate.data.source.local.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.iti.mealmate.data.auth.datasource.local.dao.UserDao;
import com.iti.mealmate.data.auth.datasource.local.entity.UserEntity;
import com.iti.mealmate.data.meal.datasource.local.dao.MealDao;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.UserFavoriteEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.UserPlanEntity;
import com.iti.mealmate.data.meal.datasource.local.converter.MealIngredientConverter;

@Database(entities = {
    UserEntity.class,
    MealEntity.class,
    UserFavoriteEntity.class,
    UserPlanEntity.class
}, version = 1, exportSchema = false)
@TypeConverters({MealIngredientConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "mealmate_db";

    public abstract UserDao userDao();
    public abstract MealDao mealDao();

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
