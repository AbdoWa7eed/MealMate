package com.iti.mealmate.ui.common;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.iti.mealmate.R;
import com.iti.mealmate.data.filter.model.entity.FilterType;

public class MealListArgs implements Parcelable {

    private final FilterType filterType;
    private final String query;

    public MealListArgs(FilterType filterType, String query) {
        this.filterType = filterType;
        this.query = query;
    }

    protected MealListArgs(Parcel in) {
        String typeName = in.readString();
        this.filterType = typeName != null ? FilterType.valueOf(typeName) : null;
        this.query = in.readString();
    }

    public static MealListArgs search(String query) {
       return new MealListArgs(FilterType.SEARCH, query);
    }

    public static MealListArgs category(String name) {
        return new MealListArgs(FilterType.CATEGORY, name);
    }

    public String resolveTitle(Context context) {
        if (filterType == FilterType.SEARCH) {
            return context.getString(R.string.search_result);
        }
        return query;
    }

    public static final Creator<MealListArgs> CREATOR = new Creator<MealListArgs>() {
        @Override
        public MealListArgs createFromParcel(Parcel in) {
            return new MealListArgs(in);
        }

        @Override
        public MealListArgs[] newArray(int size) {
            return new MealListArgs[size];
        }
    };

    public FilterType getFilterType() {
        return filterType;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filterType != null ? filterType.name() : null);
        dest.writeString(query);
    }

    public static MealListArgs fromLegacy(String mode, String value) {
        if (mode == null || value == null) return null;
        switch (mode) {
            case "category":
                return new MealListArgs(FilterType.CATEGORY, value);
            case "country":
                return new MealListArgs(FilterType.COUNTRY, value);
            case "ingredient":
                return new MealListArgs(FilterType.INGREDIENT, value);
            case "search":
                return new MealListArgs(FilterType.SEARCH, value);
            default:
                return null;
        }
    }
}

