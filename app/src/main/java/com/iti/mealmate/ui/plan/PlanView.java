package com.iti.mealmate.ui.plan;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import java.util.List;

public interface PlanView extends BaseView {

    void showPlannedMeals(List<DayPlan> plannedMealList);
}
