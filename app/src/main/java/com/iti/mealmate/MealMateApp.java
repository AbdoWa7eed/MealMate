package com.iti.mealmate;

import android.app.Application;
import android.util.Log;
import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.di.ServiceLocator;

import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class MealMateApp extends Application {
    private static final String TAG = "MealMateApp";

    @Override
    public void onCreate() {
        super.onCreate();
        setupRxJavaErrorHandler();
        ServiceLocator.init(this);
    }

    private void setupRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable instanceof UndeliverableException) {
                UndeliverableException undeliverableException = (UndeliverableException) throwable;
                Throwable cause = undeliverableException.getCause();
                if (cause instanceof NoConnectivityException) {
                    Log.w(TAG, "Undeliverable NoConnectivityException - subscription was disposed", cause);
                    return;
                }
                Log.e(TAG, "Undeliverable exception", cause);
            } else {
                Log.e(TAG, "Unhandled RxJava error", throwable);
            }
        });
    }
}