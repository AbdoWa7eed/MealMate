package com.iti.mealmate.data.auth.datasource;

import com.iti.mealmate.data.auth.mapper.AuthMapper;
import com.iti.mealmate.data.auth.model.AuthProvider;
import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.source.remote.firebase.FirebaseAuthHelper;
import com.iti.mealmate.data.source.remote.firebase.FirestoreUserHelper;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthDataSourceImpl implements AuthDataSource {

    private final FirebaseAuthHelper firebaseAuthHelper;
    private final FirestoreUserHelper firestoreUserHelper;

    public AuthDataSourceImpl(FirebaseAuthHelper firebaseAuthHelper, FirestoreUserHelper firestoreUserHelper) {
        this.firebaseAuthHelper = firebaseAuthHelper;
        this.firestoreUserHelper = firestoreUserHelper;
    }

    @Override
    public Single<UserModel> loginWithEmail(LoginRequest loginRequest) {
        return firebaseAuthHelper.loginWithEmail(loginRequest).flatMap(
                user -> firestoreUserHelper.getUser(user.getUid()));
    }

    @Override
    public Single<UserModel> registerWithEmail(RegisterRequest registerRequest) {
        return firebaseAuthHelper.registerWithEmail(registerRequest)
                .flatMap(firebaseUser -> {
                    UserModel newUser = AuthMapper.fromEmailRegistration(registerRequest, firebaseUser);
                    return firestoreUserHelper.saveUser(newUser)
                            .andThen(Single.just(newUser));
                });
    }

    @Override
    public Single<UserModel> signInWithGoogle(String idToken) {
        return firebaseAuthHelper.signInWithGoogle(idToken)
                .flatMap(firebaseUser -> {
                    UserModel newUser = AuthMapper.fromSocialLogin(firebaseUser, AuthProvider.GOOGLE);
                    return firestoreUserHelper.saveUser(newUser)
                            .andThen(Single.just(newUser));
                });
    }

    @Override
    public Single<UserModel> signInWithFacebook(String accessToken) {
        return firebaseAuthHelper.signInWithFacebook(accessToken)
                .flatMap(firebaseUser -> {
                    UserModel newUser = AuthMapper.fromSocialLogin(firebaseUser, AuthProvider.FACEBOOK);
                    return firestoreUserHelper.saveUser(newUser)
                            .andThen(Single.just(newUser));
                });
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(firebaseAuthHelper::logout);
    }

    @Override
    public boolean isLoggedIn() {
        return firebaseAuthHelper.isLoggedIn();
    }
}
