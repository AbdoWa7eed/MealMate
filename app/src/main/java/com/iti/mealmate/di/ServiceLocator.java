package com.iti.mealmate.di;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.network.AppConnectivityManagerImpl;
import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.datasource.AuthDataSourceImpl;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.auth.repo.AuthRepositoryImpl;
import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.remote.FirestoreMealHelper;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSource;
import com.iti.mealmate.data.meal.datasource.remote.MealRemoteDataSourceImpl;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepositoryImpl;
import com.iti.mealmate.data.filter.api.FilterApiService;
import com.iti.mealmate.data.filter.datasource.remote.FilterRemoteDataSource;
import com.iti.mealmate.data.filter.datasource.remote.FilterRemoteDataSourceImpl;
import com.iti.mealmate.data.filter.repo.FilterRepository;
import com.iti.mealmate.data.filter.repo.FilterRepositoryImpl;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.MealRepositoryImpl;
import com.iti.mealmate.data.source.local.db.AppDatabase;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.data.source.remote.api.ApiClient;
import com.iti.mealmate.data.source.remote.firebase.FirebaseAuthHelper;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

public final class ServiceLocator {

    private static boolean initialized = false;

    private static PreferencesHelper preferencesHelper;
    private static AuthRepository authRepository;
    private static MealRepository mealRepository;
    private static FilterRepository filterRepository;
    private static FavoriteRepository favoriteRepository;
    private static AppConnectivityManager connectivityManager;
    private static MealLocalDataSource mealLocalDataSource;

    private static AppDatabase appDatabase;

    private ServiceLocator() {}

    public static synchronized void init(Context context) {
        if (initialized) return;
        Context appContext = context.getApplicationContext();
        preferencesHelper = new PreferencesHelper(appContext);
        connectivityManager = new AppConnectivityManagerImpl(appContext);
        appDatabase = AppDatabase.getInstance(appContext);
        if (FirebaseApp.getApps(appContext).isEmpty()) {
            FirebaseApp.initializeApp(appContext);
        }
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder()
                        .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                        .build();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(settings);

        FirebaseAuthHelper firebaseAuthHelper =
                new FirebaseAuthHelper(FirebaseAuth.getInstance());
        FirestoreUserHelper firestoreUserHelper =
                new FirestoreUserHelper(firestore);
        AuthDataSource authDataSource =
                new AuthDataSourceImpl(firebaseAuthHelper, firestoreUserHelper);
        authRepository = new AuthRepositoryImpl(authDataSource, connectivityManager);

        MealApiService mealApiService =
                ApiClient.getInstance().create(MealApiService.class);
        FirestoreMealHelper firestoreMealHelper =
                new FirestoreMealHelper(firestore);
        MealRemoteDataSource mealRemoteDataSource =
                new MealRemoteDataSourceImpl(mealApiService, firestoreMealHelper);
        FavoriteLocalDataSource favoriteLocalDataSource =
                new FavoriteLocalDataSourceImpl(appDatabase.mealDao());
        mealLocalDataSource = new MealLocalDataSourceImpl(appDatabase.mealDao());
        mealRepository = new MealRepositoryImpl(mealRemoteDataSource, connectivityManager, favoriteLocalDataSource, mealLocalDataSource);
        favoriteRepository = new FavoriteRepositoryImpl(favoriteLocalDataSource, mealLocalDataSource);

        FilterApiService filterApiService =
                ApiClient.getInstance().create(FilterApiService.class);
        FilterRemoteDataSource filterRemoteDataSource =
                new FilterRemoteDataSourceImpl(filterApiService);
        filterRepository = new FilterRepositoryImpl(filterRemoteDataSource, connectivityManager);

        initialized = true;
    }

    public static PreferencesHelper getPreferencesHelper() {
        checkInit();
        return preferencesHelper;
    }

    public static AuthRepository getAuthRepository() {
        checkInit();
        return authRepository;
    }

    public static MealRepository getMealRepository() {
        checkInit();
        return mealRepository;
    }

    public static FilterRepository getFilterRepository() {
        checkInit();
        return filterRepository;
    }

    public static AppConnectivityManager getConnectivityManager() {
        checkInit();
        return connectivityManager;
    }

    public static FavoriteRepository getFavoriteRepository() {
        checkInit();
        return favoriteRepository;
    }

    public static MealLocalDataSource getMealLocalDataSource() {
        checkInit();
        return mealLocalDataSource;
    }


    private static void checkInit() {
        if (!initialized) {
            throw new IllegalStateException("ServiceLocator.init() must be called first");
        }
    }
}
