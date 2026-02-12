package com.iti.mealmate.data.meal.datasource.local.datasource.meal;


import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.datasource.local.dao.MealDao;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private final MealDao mealDao;

    public MealLocalDataSourceImpl(MealDao mealDao) {
        this.mealDao = mealDao;
    }

    @Override
    public Completable insertMeals(List<MealEntity> meals) {
        return mealDao.insertMeals(meals);
    }

    @Override
    public Completable insertMeal(MealEntity meal) {
        return mealDao.insertMeal(meal);
    }


    @Override
    public Flowable<List<MealEntity>> getDailyMeal() {
        return mealDao.getTodayMeals(CacheType.DAILY, DateUtils.todayAtStartOfDay());
    }

    @Override
    public Flowable<List<MealEntity>> getSuggestedMeals() {
        return mealDao.getTodayMeals(CacheType.TRENDING, DateUtils.todayAtStartOfDay());
    }

    @Override
    public Single<Boolean> isMealExists(String mealId) {
        return mealDao.isMealExists(mealId);
    }
}
