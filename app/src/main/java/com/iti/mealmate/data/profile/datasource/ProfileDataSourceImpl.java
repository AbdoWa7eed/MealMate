package com.iti.mealmate.data.profile.datasource;

import android.net.Uri;

import com.iti.mealmate.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Single;

public class ProfileDataSourceImpl implements ProfileDataSource {

    private final FirebaseProfileHelper profileHelper;

    public ProfileDataSourceImpl(FirebaseProfileHelper profileHelper) {
        this.profileHelper = profileHelper;
    }

    @Override
    public Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri) {
        return profileHelper.updateUserProfile(userModel, imageUri);
    }

    @Override
    public Single<String> updateProfileImage(String uid, Uri imageUri) {
        return profileHelper.updateProfileImage(uid, imageUri);
    }

    @Override
    public Single<UserModel> getUserProfile(String uid) {
        return profileHelper.getUserProfile(uid);
    }
}
