package com.iti.mealmate.di;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.network.AppConnectivityManagerImpl;
import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.datasource.AuthDataSourceImpl;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.auth.repo.AuthRepositoryImpl;
import com.iti.mealmate.data.filter.api.FilterApiService;
import com.iti.mealmate.data.filter.datasource.remote.FilterRemoteDataSource;
import com.iti.mealmate.data.filter.datasource.remote.FilterRemoteDataSourceImpl;
import com.iti.mealmate.data.filter.repo.FilterRepository;
import com.iti.mealmate.data.filter.repo.FilterRepositoryImpl;
import com.iti.mealmate.data.meal.api.MealApiService;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.local.datasource.plan.PlanLocalDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.remote.FirestoreMealHelper;
import com.iti.mealmate.data.meal.datasource.remote.meal.MealRemoteDataSource;
import com.iti.mealmate.data.meal.datasource.remote.meal.MealRemoteDataSourceImpl;
import com.iti.mealmate.data.meal.datasource.remote.sync.UserMealSyncDataSource;
import com.iti.mealmate.data.meal.datasource.remote.sync.UserMealSyncDataSourceImpl;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.MealRepositoryImpl;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepositoryImpl;
import com.iti.mealmate.data.meal.repo.plan.PlanRepository;
import com.iti.mealmate.data.meal.repo.plan.PlanRepositoryImpl;
import com.iti.mealmate.data.meal.repo.sync.SyncRepository;
import com.iti.mealmate.data.meal.repo.sync.SyncRepositoryImpl;
import com.iti.mealmate.data.profile.datasource.ProfileDataSource;
import com.iti.mealmate.data.profile.datasource.ProfileDataSourceImpl;
import com.iti.mealmate.data.profile.repo.ProfileRepository;
import com.iti.mealmate.data.profile.repo.ProfileRepositoryImpl;
import com.iti.mealmate.data.source.local.db.AppDatabase;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.data.source.remote.api.ApiClient;
import com.iti.mealmate.data.source.remote.firebase.FirebaseAuthHelper;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

public final class ServiceLocator {

    private static boolean initialized = false;

    // Local
    private static PreferencesHelper preferencesHelper;
    private static AppConnectivityManager connectivityManager;
    private static AppDatabase appDatabase;

    // Firebase
    private static FirebaseAuthHelper firebaseAuthHelper;
    private static FirestoreUserHelper firestoreUserHelper;
    private static FirestoreMealHelper firestoreMealHelper;

    // Repositories
    private static AuthRepository authRepository;
    private static MealRepository mealRepository;
    private static FavoriteRepository favoriteRepository;
    private static PlanRepository planRepository;
    private static ProfileRepository profileRepository;
    private static SyncRepository syncRepository;

    private static FilterRepository filterRepository;


    private ServiceLocator() { }

    public static synchronized void init(Context context) {
        if (initialized) return;
        Context appContext = context.getApplicationContext();

        initLocal(appContext);
        initFirebase(appContext);
        initRepositories();

        initialized = true;
    }

    public static void reset() {
        if (favoriteRepository != null) favoriteRepository.resetFetchFlag();
        if (planRepository != null) planRepository.resetFetchFlag();
    }

    public static PreferencesHelper getPreferencesHelper() {
        checkInit();
        return preferencesHelper;
    }

    public static AppDatabase getAppDatabase() {
        checkInit();
        return appDatabase;
    }

    public static AuthRepository getAuthRepository() {
        checkInit();
        return authRepository;
    }

    public static MealRepository getMealRepository() {
        checkInit();
        return mealRepository;
    }

    public static FavoriteRepository getFavoriteRepository() {
        checkInit();
        return favoriteRepository;
    }

    public static PlanRepository getPlanRepository() {
        checkInit();
        return planRepository;
    }

    public static ProfileRepository getProfileRepository() {
        checkInit();
        return profileRepository;
    }

    public static SyncRepository getSyncRepository() {
        checkInit();
        return syncRepository;
    }

    public static FilterRepository getFilterRepository() {
        checkInit();
        return filterRepository;
    }


    private static void initLocal(Context context) {
        preferencesHelper = new PreferencesHelper(context);
        connectivityManager = new AppConnectivityManagerImpl(context);
        appDatabase = AppDatabase.getInstance(context);
    }

    private static void initFirebase(Context context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.setFirestoreSettings(
                new FirebaseFirestoreSettings.Builder()
                        .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                        .build()
        );

        firebaseAuthHelper = new FirebaseAuthHelper(FirebaseAuth.getInstance());
        firestoreUserHelper = new FirestoreUserHelper(firestore, FirebaseStorage.getInstance());
        firestoreMealHelper = new FirestoreMealHelper(firestore);
    }

    private static void initRepositories() {
        // Auth
        AuthDataSource authDataSource = new AuthDataSourceImpl(firebaseAuthHelper, firestoreUserHelper);
        authRepository = new AuthRepositoryImpl(authDataSource, connectivityManager);

        // Meal
        MealApiService mealApiService = ApiClient.getInstance().create(MealApiService.class);
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSourceImpl(mealApiService, firestoreMealHelper);
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSourceImpl(appDatabase.mealDao());
        FavoriteLocalDataSource favoriteLocalDataSource = new FavoriteLocalDataSourceImpl(appDatabase.mealDao());
        UserMealSyncDataSource userSyncDataSource = new UserMealSyncDataSourceImpl(firestoreUserHelper);

        mealRepository = new MealRepositoryImpl(mealRemoteDataSource, connectivityManager, favoriteLocalDataSource, mealLocalDataSource);
        favoriteRepository = new FavoriteRepositoryImpl(favoriteLocalDataSource, mealLocalDataSource, userSyncDataSource, connectivityManager);

        // Plan
        PlanLocalDataSourceImpl planLocalDataSource = new PlanLocalDataSourceImpl(appDatabase.planDao());
        planRepository = new PlanRepositoryImpl(planLocalDataSource, mealLocalDataSource, userSyncDataSource, connectivityManager);

        // Sync
        syncRepository = new SyncRepositoryImpl(favoriteLocalDataSource, planLocalDataSource, userSyncDataSource, connectivityManager);

        // Filter
        FilterApiService filterApiService = ApiClient.getInstance().create(FilterApiService.class);
        FilterRemoteDataSource filterRemoteDataSource = new FilterRemoteDataSourceImpl(filterApiService);
        filterRepository = new FilterRepositoryImpl(filterRemoteDataSource, connectivityManager);

        // Profile
        ProfileDataSource profileDataSource = new ProfileDataSourceImpl(firestoreUserHelper);
        profileRepository = new ProfileRepositoryImpl(profileDataSource, connectivityManager);
    }


    private static void checkInit() {
        if (!initialized) throw new IllegalStateException("ServiceLocator.init() must be called first");
    }
}
