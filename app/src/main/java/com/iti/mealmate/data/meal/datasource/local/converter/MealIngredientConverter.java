package com.iti.mealmate.data.meal.datasource.local.converter;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import java.lang.reflect.Type;
import java.util.List;

public class MealIngredientConverter {

    @TypeConverter
    public static String fromList(List<MealIngredient> ingredients) {
        return new Gson().toJson(ingredients);
    }

    @TypeConverter
    public static List<MealIngredient> fromString(String value) {
        Type listType = new TypeToken<List<MealIngredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
