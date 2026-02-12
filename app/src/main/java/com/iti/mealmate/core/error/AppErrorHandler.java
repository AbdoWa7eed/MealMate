package com.iti.mealmate.core.error;

import android.util.Log;

import com.google.firebase.FirebaseException;
import com.iti.mealmate.core.network.NoConnectivityException;

public final class AppErrorHandler {

    private static final String TAG = "AppErrorHandler";

    private AppErrorHandler() {
    }

    public static Exception handle(Throwable throwable) {
        Log.e(TAG, "Handled exception", throwable);

        if (throwable instanceof FirebaseException) {
            return FirebaseErrorHandler.handle(throwable);
        }

        if (throwable instanceof NoConnectivityException) {
            return (NoConnectivityException) throwable;
        }

        return new Exception("An Unknown Error Occurred. Please try again later.");
    }
}


