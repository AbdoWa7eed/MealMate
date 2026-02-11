package com.iti.mealmate.ui.plan;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;

public interface PlanPresenter extends BasePresenter{

    void loadPlans();
    void removeMeal(PlannedMeal meal);

    void loadCurrentWeek();

    void loadNextWeek();

}
