package com.iti.mealmate.data.filter.model.entity;

public class FilterItem {

    private String id;
    private String name;
    private String imageUrl;
    private FilterType filterType;

    public FilterItem() {
    }

    public FilterItem(String id, String name, String imageUrl, FilterType filterType) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.filterType = filterType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}


