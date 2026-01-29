package com.iti.mealmate.data.repo;

import com.iti.mealmate.data.datasource.local.prefs.PreferencesDataSource;

public class AppStartupRepository {

    private final PreferencesDataSource preferencesDataSource;

    public AppStartupRepository(PreferencesDataSource preferencesDataSource) {
        this.preferencesDataSource = preferencesDataSource;
    }

    public boolean isOnboardingCompleted() {
        return preferencesDataSource.isOnboardingCompleted();
    }

    public void setOnboardingCompleted() {
        preferencesDataSource.setOnboardingCompleted();
    }
}
