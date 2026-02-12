package com.iti.mealmate.ui.home;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;

public interface HomeView extends BaseView {
    void showMealOfTheDay(Meal meal);
    void showTrendingMeals(List<Meal> meals);
}
