package com.iti.mealmate.ui.mealdetail.presenter;

import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.ui.mealdetail.MealDetailsPresenter;
import com.iti.mealmate.ui.mealdetail.MealDetailsView;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {

    private final MealDetailsView view;

    private Meal meal;

    public MealDetailsPresenterImpl(MealDetailsView view) {
        this.view = view;
    }

    @Override
    public void setMeal(Meal meal) {
        if (meal != null) {
            this.meal = meal;
            displayMealSimpleDetails(meal);
            displayIngredients(meal);
            displayInstructions(meal);
        }
    }

    private void displayMealSimpleDetails(Meal meal) {
        view.showMealName(meal.getName());
        view.showCountry(meal.getArea());
        view.showMealImage(meal.getThumbnailUrl());
    }

    private void displayIngredients(Meal meal) {
        List<MealIngredient> ingredients = meal.getIngredients();
        if (ingredients != null) {
            view.showItemsCount(ingredients.size());
            view.showIngredients(ingredients);
        } else {
            view.showItemsCount(0);
        }
    }

    private void displayInstructions(Meal meal) {
        if (meal.getInstructions() != null && !meal.getInstructions().isEmpty()) {
            String[] stepsArray = meal.getInstructions().split("\\r?\\n");
            List<String> steps = new ArrayList<>();

            for (String step : stepsArray) {
                step = step.trim();
                if (step.isEmpty()) continue;

                if (step.toLowerCase().matches("^step\\s+\\d+.*")) continue;
                if (step.matches("^\\d+\\..*")) continue;
                steps.add(step);
            }

            view.showPreparationSteps(steps);
        }
    }
    @Override
    public void addToPlan() {
        // TODO: Implement logic to add to plan

    }

    @Override
    public void onVideoClicked() {
        view.showVideoLoading();
        view.showVideo(meal.getYoutubeUrl());
    }


    @Override
    public void onDestroy() {
        // clean up
    }

}
