package com.iti.mealmate.data.filter.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesBaseResponse {

    @SerializedName("categories")
    private List<CategoryResponse> categories;

    public CategoriesBaseResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}


