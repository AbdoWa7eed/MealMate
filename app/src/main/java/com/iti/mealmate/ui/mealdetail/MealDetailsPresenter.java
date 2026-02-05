package com.iti.mealmate.ui.mealdetail;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.meal.model.entity.Meal;

public interface MealDetailsPresenter extends BasePresenter {
    void handleIntent(Meal meal, String mealId);
    void retry();
    void addToPlan();
    void onVideoClicked();
}
