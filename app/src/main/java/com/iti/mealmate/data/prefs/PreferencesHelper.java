package com.iti.mealmate.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper{

    private static final String PREFS_NAME = "MealMatePrefs";
    private static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";
    private final SharedPreferences prefs;

    public PreferencesHelper(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setOnboardingCompleted() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply();
    }

    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false);
    }

}