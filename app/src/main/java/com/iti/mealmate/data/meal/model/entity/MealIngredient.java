package com.iti.mealmate.data.meal.model.entity;

import java.io.Serializable;
import java.text.Normalizer;

public class MealIngredient implements Serializable {
    private String name;
    private String measure;

    public MealIngredient() {
    }

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
        if (name == null || name.isEmpty()) return"";
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .toLowerCase()
                .replace(" ", "_");
        return "https://www.themealdb.com/images/ingredients/" + normalized + ".png";
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public void setMeasure(String measure) {
        this.measure = measure != null ? measure.trim() : "";
    }
}
