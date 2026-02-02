package com.iti.mealmate.util;


import androidx.annotation.NonNull;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class FacebookSignInHelper {

    public static Single<String> signIn(FacebookLoginProvider provider) {
        return Single.create(emitter -> {
            var callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance()
                    .registerCallback(callbackManager, createCallback(emitter));
            LoginManager.getInstance()
                    .logInWithReadPermissions(provider.getFragment(), callbackManager,Arrays.asList("email", "public_profile"));
            emitter.setCancellable(() -> LoginManager.getInstance().unregisterCallback(callbackManager));
        });
    }

    private static FacebookCallback<LoginResult> createCallback(SingleEmitter<String> emitter) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(result.getAccessToken().getToken());
                }
            }

            @Override
            public void onCancel() {
                if (!emitter.isDisposed()) {
                    emitter.onError(new Exception("Facebook login cancelled"));
                }
            }

            @Override
            public void onError(@NonNull FacebookException error) {
                if (!emitter.isDisposed()) {
                    emitter.onError(error);
                }
            }
        };
    }


}
