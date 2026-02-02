package com.iti.mealmate.data.auth.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;

public class AuthMapper {

    public static UserModel toUserModel(RegisterRequest request, FirebaseUser firebaseUser) {
        return new UserModel(
                firebaseUser.getUid(),
                request.getEmail(),
                request.getName(),
                null,
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
    }

    public static UserModel toUserModelFromFirebaseUser(FirebaseUser firebaseUser) {
        return new UserModel(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                firebaseUser.getDisplayName(),
                null,
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
    }

}