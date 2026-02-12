package com.iti.mealmate.data.meal.model.mapper;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
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

    public static List<Meal> cachedEntitiesToDomain(List<MealEntity> entities) {
        if (entities == null || entities.isEmpty()) return List.of();
        return entities.stream()
                .map(MealMapper::cachedEntityToDomain)
                .collect(Collectors.toList());
    }

    public static Meal cachedEntityToDomain(MealEntity entity) {
        if (entity == null) return null;
        return new Meal(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getArea(),
                entity.getInstructions(),
                entity.getThumbnailUrl(),
                entity.getYoutubeUrl(),
                entity.getSourceUrl(),
                entity.getIngredients(),
                entity.isFavorite());
    }

    public static List<MealEntity> domainToCachedEntities(List<Meal> meals, CacheType type) {
        if (meals == null || meals.isEmpty()) return List.of();
        return meals.stream()
                .map(meal -> MealMapper.domainToCachedEntity(meal, type))
                .collect(Collectors.toList());
    }

    public static MealEntity domainToCachedEntity(Meal meal, CacheType type) {
        if (meal == null)
            return null;
        return new MealEntity(
                meal.getId(),
                meal.getName(),
                meal.getCategory(),
                meal.getArea(),
                meal.getInstructions(),
                meal.getThumbnailUrl(),
                meal.getYoutubeUrl(),
                meal.getSourceUrl(),
                meal.getIngredients(),
                meal.isFavorite(),
                type,
                DateUtils.todayAtStartOfDay()
        );
    }

}
