package com.iti.mealmate.di;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.datasource.AuthDataSourceImpl;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.auth.repo.AuthRepositoryImpl;
import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.datasource.remote.FirestoreMealHelper;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSource;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSourceImpl;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.MealRepositoryImpl;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.data.source.remote.api.ApiClient;
import com.iti.mealmate.data.source.remote.firebase.FirebaseAuthHelper;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

public class ServiceLocator {
    private static PreferencesHelper preferencesHelper;

    private static AuthRepository authRepository;
    private static AuthDataSource authDataSource;

    private static FirebaseAuthHelper firebaseAuthHelper;

    private static FirestoreUserHelper firestoreUserHelper;

    private static MealRepository mealRepository;
    private static MealRemoteDataSource mealRemoteDataSource;
    private static MealApiService mealApiService;
    private static FirestoreMealHelper firestoreMealHelper;

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

        if (mealRepository == null) {
            mealApiService = ApiClient.getInstance().create(MealApiService.class);
            firestoreMealHelper = new FirestoreMealHelper(FirebaseFirestore.getInstance());
            mealRemoteDataSource = new MealRemoteDataSourceImpl(mealApiService, firestoreMealHelper);
            mealRepository = new MealRepositoryImpl(mealRemoteDataSource);
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

    public static MealRepository getMealRepository() {
        if (mealRepository == null) {
            throw new IllegalStateException("ServiceLocator not initialized!");
        }
        return mealRepository;
    }
}