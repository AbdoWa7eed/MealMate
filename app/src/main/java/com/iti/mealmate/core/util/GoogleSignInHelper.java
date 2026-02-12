package com.iti.mealmate.core.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.iti.mealmate.R;

import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class GoogleSignInHelper {

    public static Single<String> signIn(Context context) {
        return Single.create(emitter -> {
            String serverClientId = context.getString(R.string.default_web_client_id);
            GetCredentialRequest request = buildCredentialRequest(serverClientId);
            CredentialManager credentialManager = CredentialManager.create(context);
            credentialManager.getCredentialAsync(
                    context,
                    request,
                    null,
                    Executors.newSingleThreadExecutor(),
                    createCallback(emitter)
            );
        });
    }

    private static GetCredentialRequest buildCredentialRequest(String serverClientId) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(serverClientId)
                .build();
        return new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
    }

    private static CredentialManagerCallback<GetCredentialResponse, GetCredentialException>
    createCallback(SingleEmitter<String> emitter) {
        return new CredentialManagerCallback<>() {
            @Override
            public void onResult(GetCredentialResponse result) {
                handleCredentialResult(result, emitter);
            }

            @Override
            public void onError(@NonNull GetCredentialException e) {
                emitter.onError(e);
            }
        };
    }

    private static void handleCredentialResult(GetCredentialResponse result,
                                               SingleEmitter<String> emitter) {
        if (emitter.isDisposed()) return;

        try {
            Credential credential = result.getCredential();

            if (credential instanceof CustomCredential) {
                if(credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                    Bundle credentialData = credential.getData();
                    GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);
                    emitter.onSuccess(googleIdTokenCredential.getIdToken());
                    return;
                }
                emitter.onError(new Exception("Unexpected credential type"));
            } else {
                emitter.onError(new Exception("Unexpected credential type"));
            }
        } catch (Exception e) {
            emitter.onError(e);
        }
    }
}