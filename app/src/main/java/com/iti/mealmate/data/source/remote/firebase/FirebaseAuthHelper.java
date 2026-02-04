package com.iti.mealmate.data.source.remote.firebase;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.util.RxTask;

import io.reactivex.rxjava3.core.Single;

public class FirebaseAuthHelper {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthHelper(FirebaseAuth auth) {
        this.firebaseAuth = auth;
    }

    public Single<FirebaseUser> loginWithEmail(LoginRequest loginRequest) {
        return RxTask.firebaseToSingleTask(
                firebaseAuth.signInWithEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
        ).flatMap(authResult -> {
                    if (authResult.getUser() != null) {
                        return Single.just(authResult.getUser());
                    } else {
                        return Single.error(new Exception("User authentication failed"));
                    }
                });
    }

    public Single<FirebaseUser> registerWithEmail(RegisterRequest registerRequest) {

        return RxTask.firebaseToSingleTask(
                firebaseAuth.createUserWithEmailAndPassword(registerRequest.getEmail(), registerRequest.getPassword())
        ).flatMap(authResult -> {
                    if (authResult.getUser() != null) {
                        return Single.just(authResult.getUser());
                    } else {
                        return Single.error(new Exception("User authentication failed"));
                    }
                });
    }

    public Single<FirebaseUser> signInWithGoogle(String idToken) {
        return signInWithCredential(GoogleAuthProvider.getCredential(idToken, null));
    }

    public Single<FirebaseUser> signInWithFacebook(String token) {
        return signInWithCredential(FacebookAuthProvider.getCredential(token));
    }

    public Single<FirebaseUser> signInWithCredential(AuthCredential credential) {
        return RxTask.firebaseToSingleTask(
                firebaseAuth.signInWithCredential(credential)
        ).flatMap(authResult -> {
                    if (authResult.getUser() != null) {
                        return Single.just(authResult.getUser());
                    } else {
                        return Single.error(new Exception("User authentication failed"));
                    }
                });
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void logout() {
        firebaseAuth.signOut();
    }

}
