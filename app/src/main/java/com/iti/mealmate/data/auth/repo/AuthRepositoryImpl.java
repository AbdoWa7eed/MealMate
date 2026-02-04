package com.iti.mealmate.data.auth.repo;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.data.auth.datasource.AuthDataSource;
import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.util.RxTask;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepositoryImpl implements AuthRepository {

    private final AuthDataSource authDataSource;
    private final AppConnectivityManager connectivityManager;

    public AuthRepositoryImpl(AuthDataSource authDataSource,
                              AppConnectivityManager connectivityManager) {
        this.authDataSource = authDataSource;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Single<UserModel> loginWithEmail(LoginRequest loginRequest) {
        return RxTask.withConnectivity(
                authDataSource.loginWithEmail(loginRequest),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<UserModel> registerWithEmail(RegisterRequest registerRequest) {
        return RxTask.withConnectivity(
                authDataSource.registerWithEmail(registerRequest),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<UserModel> signInWithGoogle(String idToken) {
        return RxTask.withConnectivity(
                authDataSource.signInWithGoogle(idToken),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<UserModel> signInWithFacebook(String accessToken) {
        return RxTask.withConnectivity(
                authDataSource.signInWithFacebook(accessToken),
                connectivityManager
        ).onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public boolean isUserLoggedIn() {
        return authDataSource.isLoggedIn();
    }

    @Override
    public Completable logout() {
        return authDataSource.logout()
                .onErrorResumeNext(throwable -> Completable.error(AppErrorHandler.handle(throwable)));
    }
}
