package com.iti.mealmate.data.meal.model.entity;

import java.util.List;

public class Meal {
    private String id;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String thumbnailUrl;
    private String youtubeUrl;
    private String sourceUrl;
    private List<MealIngredient> ingredients;

    private boolean isFavorite;

    public Meal() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setIngredients(List<MealIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
