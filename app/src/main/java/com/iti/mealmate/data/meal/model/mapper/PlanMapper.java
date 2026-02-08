package com.iti.mealmate.data.meal.model.mapper;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealDetailEntity;
import com.iti.mealmate.data.meal.datasource.local.entity.PlannedMealEntity;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class PlanMapper {

    private PlanMapper(){}


    public static PlannedMeal toDomainEntity(PlannedMealDetailEntity mealDetailEntity){

        return new PlannedMeal(
                MealMapper.cachedEntityToDomain(mealDetailEntity.getMeal()),
                DateUtils.timestampToLocalDate(mealDetailEntity.getPlannedMeal().getPlannedDate())
        );
    }

    public static List<PlannedMeal> toDomainEntityList(List<PlannedMealDetailEntity> mealDetailEntities){

        return mealDetailEntities
                .stream()
                .map(PlanMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    public static PlannedMealEntity createPlannedEntity(String mealId , LocalDate date){
        long plannedDateMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new PlannedMealEntity(mealId, plannedDateMillis);
    }
}
