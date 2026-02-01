package com.iti.mealmate.di;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.datasource.AuthDataSourceImpl;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.auth.repo.AuthRepositoryImpl;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.data.source.remote.firebase.FirebaseAuthHelper;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

public class ServiceLocator {
    private static PreferencesHelper preferencesHelper;

    private static AuthRepository authRepository;
    private static AuthDataSource authDataSource;

    private static FirebaseAuthHelper firebaseAuthHelper;

    private static FirestoreUserHelper firestoreUserHelper;

    public static void init(Context context) {
        if (preferencesHelper == null) {
            preferencesHelper = new PreferencesHelper(context.getApplicationContext());
        }

        if (authRepository == null) {
            firebaseAuthHelper = new FirebaseAuthHelper(FirebaseAuth.getInstance());
            firestoreUserHelper = new FirestoreUserHelper(FirebaseFirestore.getInstance());
            authDataSource = new AuthDataSourceImpl(firebaseAuthHelper, firestoreUserHelper);
            authRepository = new AuthRepositoryImpl(authDataSource);
        }
    }

    public static PreferencesHelper getPreferencesHelper() {
        if (preferencesHelper == null) {
            throw new IllegalStateException("ServiceLocator not initialized!");
        }
        return preferencesHelper;
    }


    public static AuthRepository getAuthRepository() {
        if (authRepository == null) {
            throw new IllegalStateException("ServiceLocator not initialized!");
        }
        return authRepository;
    }
}