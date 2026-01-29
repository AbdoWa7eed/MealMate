package com.iti.mealmate;

import android.app.Application;
import com.iti.mealmate.di.ServiceLocator;

public class MealMateApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocator.init(this);
    }
}