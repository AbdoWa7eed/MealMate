package com.iti.mealmate.ui.mealdetail;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.meal.model.entity.Meal;

public interface MealDetailsPresenter extends BasePresenter {
    void setMeal(Meal meal);
    void addToPlan();
    void onVideoClicked();
}
