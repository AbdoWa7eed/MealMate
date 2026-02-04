package com.iti.mealmate.core.error;

import android.util.Log;

import com.google.firebase.FirebaseException;

public final class AppErrorHandler {

    private static final String TAG = "AppErrorHandler";

    private AppErrorHandler() {
    }

    public static Exception handle(Throwable throwable) {
        Log.e(TAG, "Handled exception", throwable);

        if (throwable instanceof FirebaseException) {
            return FirebaseErrorHandler.handle(throwable);
        }

        return new Exception(
                throwable.getMessage() != null && !throwable.getMessage().isEmpty()
                        ? throwable.getMessage()
                        : "Something went wrong. Please try again later."
        );
    }
}


