package com.iti.mealmate.data.meal.datasource.local.datasource.plan;

import com.iti.mealmate.data.meal.datasource.local.dao.PlanDao;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealsEntity;
import com.iti.mealmate.data.meal.model.entity.Meal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PlanLocalDataSourceImpl implements PlanLocalDataSource {

    private final PlanDao planDao;

    public PlanLocalDataSourceImpl(PlanDao planDao) {
        this.planDao = planDao;
    }

    @Override
    public Completable addMealToPlan(Meal meal, LocalDate date) {
        long plannedDateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        PlannedMealsEntity entity = new PlannedMealsEntity(meal.getId(), plannedDateMillis);
        return planDao.addToPlan(entity);
    }

    @Override
    public Completable removeMealFromDay(String mealId, LocalDate date) {
        long plannedDateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return planDao.removeMealFromDay(mealId, plannedDateMillis);
    }

    @Override
    public Completable clearAllPlans() {
        return planDao.clearAllPlans();
    }

    @Override
    public Flowable<List<PlannedMealDetailEntity>> getMealsForDay(LocalDate date) {
        long plannedDateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return planDao.getPlannedMealsWithDetails(plannedDateMillis);
    }

    @Override
    public Flowable<List<PlannedMealDetailEntity>> getMealsForDateRange(LocalDate startDate, LocalDate endDate) {
        long startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return planDao.getPlannedMealsWithDetailsForRange(startMillis, endMillis);
    }
}
