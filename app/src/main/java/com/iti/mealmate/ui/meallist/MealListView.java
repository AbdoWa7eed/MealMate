package com.iti.mealmate.ui.meallist;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.MealLight;

import java.util.List;

public interface MealListView extends BaseView {
    void showMeals(List<MealLight> meals);
    void showEmptyState();
    void hideEmptyState();
}


