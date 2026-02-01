package com.iti.mealmate.data.auth.datasource;

import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthDataSource {

    Single<UserModel> loginWithEmail(LoginRequest loginRequest);

    Single<UserModel> registerWithEmail(RegisterRequest registerRequest);

    Single<UserModel> signInWithGoogle(String idToken);

    Single<UserModel> signInWithFacebook(String accessToken);
    Completable logout();
    boolean isLoggedIn();
}
