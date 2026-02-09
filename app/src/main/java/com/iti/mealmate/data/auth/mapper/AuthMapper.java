package com.iti.mealmate.data.auth.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.iti.mealmate.data.auth.model.AuthProvider;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;

public class AuthMapper {

    public static UserModel fromEmailRegistration(RegisterRequest request, FirebaseUser firebaseUser) {
        return new UserModel(
                firebaseUser.getUid(),
                request.getEmail(),
                request.getName(),
                null,
                AuthProvider.BASIC,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0,
                0,
                0
        );
    }

    public static UserModel fromSocialLogin(FirebaseUser firebaseUser, AuthProvider provider) {
        String profileImageUrl = firebaseUser.getPhotoUrl() != null
                ? firebaseUser.getPhotoUrl().toString()
                : null;
        return new UserModel(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                firebaseUser.getDisplayName(),
                profileImageUrl,
                provider,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0,
                0,
                0
        );
    }

}