package com.iti.mealmate.ui.home;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.meal.model.entity.Meal;

public interface HomePresenter extends BasePresenter {
    void loadHomeData();
    void toggleFavorite(Meal meal);
}
