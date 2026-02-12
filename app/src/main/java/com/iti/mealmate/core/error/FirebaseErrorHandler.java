package com.iti.mealmate.core.error;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestoreException;


public final class FirebaseErrorHandler {

    private FirebaseErrorHandler() {
    }

    public static Exception handle(Throwable throwable) {
        if (throwable instanceof FirebaseAuthInvalidUserException) {
            return new Exception("This account does not exist or has been disabled.");
        } else if (throwable instanceof FirebaseAuthInvalidCredentialsException) {
            return new Exception("The email or password you entered is incorrect.");
        } else if (throwable instanceof FirebaseAuthUserCollisionException) {
            return new Exception("An account with this email already exists.");
        } else if (throwable instanceof FirebaseAuthException) {
            return new Exception(((FirebaseAuthException) throwable).getMessage());
        }

        if (throwable instanceof FirebaseFirestoreException) {
            FirebaseFirestoreException e = (FirebaseFirestoreException) throwable;
            switch (e.getCode()) {
                case PERMISSION_DENIED:
                    return new Exception("You don't have permission to access this data.");
                case NOT_FOUND:
                    return new Exception("The requested data was not found.");
                case UNAVAILABLE:
                    return new Exception("Service is temporarily unavailable. Please try again later.");
                default:
                    return new Exception(
                            e.getMessage() != null && !e.getMessage().isEmpty()
                                    ? e.getMessage()
                                    : "A data error occurred. Please try again."
                    );
            }
        }

        if (throwable instanceof FirebaseNetworkException) {
            return new Exception("Network error. Please check your internet connection and try again.");
        }

        return new Exception("Something went wrong with the server. Please try again later.");
    }
}


