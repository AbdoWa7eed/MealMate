package com.iti.mealmate.data.meal.model.mapper;

import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.data.meal.model.response.MealResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealMapper {

    public static Meal toEntity(MealResponse response) {
        List<MealIngredient> ingredients = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ingredient = response.getStrIngredient(i);
            String measure = response.getStrMeasure(i);

            if (ingredient != null && !ingredient.trim().isEmpty()) {
                ingredients.add(new MealIngredient(ingredient, measure != null ? measure : ""));
            }
        }

        return new Meal(
                response.getIdMeal(),
                response.getStrMeal(),
                response.getStrCategory(),
                response.getStrArea(),
                response.getStrInstructions(),
                response.getStrMealThumb(),
                response.getStrYoutube(),
                response.getStrSource(),
                ingredients,
                false
        );
    }

    public static MealLight toLightEntity(MealResponse response) {
        return new MealLight(
                response.getIdMeal(),
                response.getStrMeal(),
                response.getStrMealThumb()
        );
    }

    public static List<Meal> toEntityList(List<MealResponse> response) {
        if (response == null) {
            return new ArrayList<>();
        }
        return response.stream()
                .map(MealMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static List<MealLight> toLightList(List<MealResponse> responses) {
        if (responses == null) {
            return new ArrayList<>();
        }
        return responses.stream()
                .map(meal -> new MealLight(
                        meal.getIdMeal(),
                        meal.getStrMeal(),
                        meal.getStrMealThumb()
                ))
                .collect(Collectors.toList());
    }
}
