package com.iti.mealmate.data.meal.model.mapper;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealEntity;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlanMapper {

    private PlanMapper() {
    }


    public static PlannedMeal toPlannedMealEntity(PlannedMealDetailEntity mealDetailEntity) {
        return new PlannedMeal(
                MealMapper.cachedEntityToDomain(mealDetailEntity.getMeal()),
                DateUtils.timestampToLocalDate(mealDetailEntity.getPlannedMeal().getPlannedDate())
        );
    }


    public static PlannedMealEntity createPlannedEntity(PlannedMeal plannedMeal) {
        return new PlannedMealEntity(
                plannedMeal.getMeal().getId(),
                DateUtils.dateToTimeStamp(plannedMeal.getPlannedDate()));
    }


    public static List<DayPlan> groupByDay(List<PlannedMealDetailEntity> mealDetails) {
        return mealDetails.stream()
                .map(PlanMapper::toPlannedMealEntity)
                .collect(Collectors.groupingBy(PlannedMeal::getPlannedDate, Collectors.toList()))
                .entrySet().stream()
                .map(e -> new DayPlan(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(DayPlan::getDate))
                .collect(Collectors.toList());
    }
}
