package com.iti.mealmate.data.meal.model.entity;

public class MealLight {

    private final String id;
    private final String name;
    private final String thumbnailUrl;

    public MealLight(String id, String name, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}


