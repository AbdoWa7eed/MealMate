package com.iti.mealmate.ui.mealdetail;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;

import java.util.List;

public interface MealDetailsView extends BaseView {
    void showMealName(String name);
    void showMealImage(String url);
    void showCountry(String country);
    void showIngredients(List<MealIngredient> ingredients);
    void showPreparationSteps(List<String> steps);
    void showItemsCount(int count);
}
