package com.iti.mealmate.data.datasource.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesDataSourceImpl implements PreferencesDataSource {

    private static final String PREFS_NAME = "MealMatePrefs";
    private static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";
    private final SharedPreferences prefs;

    public PreferencesDataSourceImpl(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setOnboardingCompleted() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply();
    }

    @Override
    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false);
    }

}