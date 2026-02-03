package com.iti.mealmate.data.meal.model.entity;

import java.util.List;

public class Meal {
    private final String id;
    private final String name;
    private final String category;
    private final String area;
    private final String instructions;
    private final String thumbnailUrl;
    private final String youtubeUrl;
    private final String sourceUrl;
    private final List<MealIngredient> ingredients;

    private boolean isFavorite;

    public Meal(String id, String name, String category, String area,
                String instructions, String thumbnailUrl, String youtubeUrl,
                String sourceUrl, List<MealIngredient> ingredients,
                boolean isFavorite
                ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbnailUrl = thumbnailUrl;
        this.youtubeUrl = youtubeUrl;
        this.sourceUrl = sourceUrl;
        this.ingredients = ingredients;
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public List<MealIngredient> getIngredients() {
        return ingredients;
    }
}
