package com.iti.mealmate.ui.favorites;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.Meal;
import java.util.List;

public interface FavoriteView extends BaseView {
    void showFavorites(List<Meal> favorites);
    void showEmptyState();
}
