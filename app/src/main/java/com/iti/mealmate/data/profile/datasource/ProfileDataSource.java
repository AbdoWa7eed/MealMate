package com.iti.mealmate.data.profile.datasource;

import android.net.Uri;

import com.iti.mealmate.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Single;

public interface ProfileDataSource {

    Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri);

    Single<String> updateProfileImage(String uid, Uri imageUri);

    Single<UserModel> getUserProfile(String uid);
}
