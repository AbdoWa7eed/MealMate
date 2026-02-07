package com.iti.mealmate.ui.favorites;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.meal.model.entity.Meal;

public interface FavoritePresenter extends BasePresenter {
    void loadFavorites();
    void toggleFavorite(Meal meal);
    void onMealClicked(Meal meal);
}
