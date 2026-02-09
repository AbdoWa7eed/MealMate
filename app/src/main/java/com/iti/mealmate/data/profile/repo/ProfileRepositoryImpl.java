package com.iti.mealmate.data.profile.repo;

import android.net.Uri;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.profile.datasource.ProfileDataSource;

import io.reactivex.rxjava3.core.Single;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileDataSource profileDataSource;
    private final AppConnectivityManager connectivityManager;

    public ProfileRepositoryImpl(ProfileDataSource profileDataSource,
                                 AppConnectivityManager connectivityManager) {
        this.profileDataSource = profileDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri) {
        return RxTask.withConnectivity(
                profileDataSource.updateUserProfile(userModel, imageUri),
                connectivityManager
        ).onErrorResumeNext(
                throwable -> Single.error(AppErrorHandler.handle(throwable))
        );
    }

    @Override
    public Single<String> updateProfileImage(String uid, Uri imageUri) {
        return RxTask.withConnectivity(
                profileDataSource.updateProfileImage(uid, imageUri),
                connectivityManager
        ).onErrorResumeNext(
                throwable -> Single.error(AppErrorHandler.handle(throwable))
        );
    }

    @Override
    public Single<UserModel> getUserProfile(String uid) {
        return RxTask.withConnectivity(
                profileDataSource.getUserProfile(uid),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

}
