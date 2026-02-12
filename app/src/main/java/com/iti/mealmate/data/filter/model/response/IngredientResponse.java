package com.iti.mealmate.data.filter.model.response;

import com.google.gson.annotations.SerializedName;

public class IngredientResponse {

    @SerializedName("idIngredient")
    private String idIngredient;

    @SerializedName("strIngredient")
    private String strIngredient;

    @SerializedName("strDescription")
    private String strDescription;

    @SerializedName("strThumb")
    private String strThumb;

    @SerializedName("strType")
    private String strType;

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrThumb() {
        return strThumb;
    }

    public String getStrType() {
        return strType;
    }
}


