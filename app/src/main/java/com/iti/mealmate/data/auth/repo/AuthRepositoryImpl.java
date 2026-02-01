package com.iti.mealmate.data.auth.repo;

import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthDataSource authDataSource;

    public AuthRepositoryImpl(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }


    @Override
    public Single<UserModel> loginWithEmail(LoginRequest loginRequest) {
        return authDataSource.loginWithEmail(loginRequest);
    }

    @Override
    public Single<UserModel> registerWithEmail(RegisterRequest registerRequest) {
        return authDataSource.registerWithEmail(registerRequest);
    }

    @Override
    public Single<UserModel> signInWithGoogle(String idToken) {
        return authDataSource.signInWithGoogle(idToken);
    }

    @Override
    public Single<UserModel> signInWithFacebook(String accessToken) {
        return authDataSource.signInWithFacebook(accessToken);
    }

    @Override
    public boolean isUserLoggedIn() {
        return authDataSource.isLoggedIn();
    }

    @Override
    public Completable logout() {
        return authDataSource.logout();
    }
}
