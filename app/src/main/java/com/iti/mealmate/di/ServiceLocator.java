package com.iti.mealmate.di;

import android.content.Context;

import com.iti.mealmate.data.prefs.PreferencesHelper;

public class ServiceLocator {
    private static PreferencesHelper preferencesHelper;

    public static void init(Context context) {
        if (preferencesHelper == null) {
            preferencesHelper = new PreferencesHelper(context.getApplicationContext());
        }
    }

    public static PreferencesHelper getPreferencesHelper() {
        if (preferencesHelper == null) {
            throw new IllegalStateException("ServiceLocator not initialized!");
        }
        return preferencesHelper;
    }
}