package com.iti.mealmate.data.profile.datasource;

import android.net.Uri;

import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

import io.reactivex.rxjava3.core.Single;

public class ProfileDataSourceImpl implements ProfileDataSource {

    private final FirestoreUserHelper firestoreUserHelper;

    public ProfileDataSourceImpl(FirestoreUserHelper firestoreUserHelper) {
        this.firestoreUserHelper = firestoreUserHelper;
    }

    @Override
    public Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri) {
        return firestoreUserHelper.updateUserProfile(userModel, imageUri);
    }

    @Override
    public Single<String> updateProfileImage(String uid, Uri imageUri) {
        return firestoreUserHelper.updateProfileImage(uid, imageUri);
    }

    @Override
    public Single<UserModel> getUserProfile(String uid) {
        return firestoreUserHelper.getUser(uid);
    }
}
