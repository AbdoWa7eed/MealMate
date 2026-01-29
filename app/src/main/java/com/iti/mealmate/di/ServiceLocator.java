package com.iti.mealmate.di;

import android.content.Context;

import com.iti.mealmate.data.datasource.local.prefs.PreferencesDataSource;
import com.iti.mealmate.data.datasource.local.prefs.PreferencesDataSourceImpl;
import com.iti.mealmate.data.repo.AppStartupRepository;

public class ServiceLocator {
    private static AppStartupRepository appStartupRepository;

    public static void init(Context context) {
        if (appStartupRepository == null) {
            PreferencesDataSource prefs = new PreferencesDataSourceImpl(context.getApplicationContext());
            appStartupRepository = new AppStartupRepository(prefs);
        }
    }

    public static AppStartupRepository getAppStartupRepository() {
        if (appStartupRepository == null) {
            throw new IllegalStateException("ServiceLocator not initialized!");
        }
        return appStartupRepository;
    }
}