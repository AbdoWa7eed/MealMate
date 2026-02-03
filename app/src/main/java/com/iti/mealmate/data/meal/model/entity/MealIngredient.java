package com.iti.mealmate.data.meal.model.entity;

import java.text.Normalizer;

public class MealIngredient {
    private final String name;
    private final String measure;

    public MealIngredient(String name, String measure) {
        this.name = name != null ? name.trim() : "";
        this.measure = measure != null ? measure.trim() : "";
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getImageUrl() {
        if (name.isEmpty()) return"";
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .toLowerCase()
                .replace(" ", "_");
        return "https://www.themealdb.com/images/ingredients/" + normalized + ".png";
    }
}
