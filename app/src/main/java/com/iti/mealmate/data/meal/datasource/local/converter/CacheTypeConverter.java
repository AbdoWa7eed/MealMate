package com.iti.mealmate.data.meal.datasource.local.converter;

import androidx.room.TypeConverter;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;

public class CacheTypeConverter {

    @TypeConverter
    public static String fromEnum(CacheType cacheType) {
        return cacheType == null ? CacheType.NONE.name() : cacheType.name();
    }

    @TypeConverter
    public static CacheType fromString(String value) {
        try {
            return value == null ? CacheType.NONE : CacheType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return CacheType.NONE;
        }
    }
}
