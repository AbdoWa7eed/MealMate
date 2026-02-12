package com.iti.mealmate.data.meal.datasource.local.datasource.plan;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.datasource.local.dao.PlanDao;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealEntity;
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
    public Completable addMealToPlan(PlannedMealEntity plannedMeal) {
        return planDao.addToPlan(plannedMeal);
    }

    @Override
    public Completable removeMealFromDay(PlannedMealEntity plannedMeal) {
        return planDao.removeMealFromDay(plannedMeal.getMealId(), plannedMeal.getPlannedDate());
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

        return planDao.getPlannedMealsWithDetailsForRange(
                DateUtils.dateToTimeStamp(startDate),
                DateUtils.dateToTimeStamp(endDate));
    }
}
