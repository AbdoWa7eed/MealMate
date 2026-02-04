package com.iti.mealmate.util;

import com.google.android.gms.tasks.Task;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.network.NoConnectivityException;

import io.reactivex.rxjava3.core.Single;

public class RxTask {

    public static <T> Single<T> firebaseToSingleTask(Task<T> task) {
        return Single.create(emitter ->
                task.addOnSuccessListener(emitter::onSuccess)
                        .addOnFailureListener(emitter::onError)
        );
    }

    public static <T> Single<T> withConnectivity(
            Single<T> source,
            AppConnectivityManager connectivityManager
    ) {
        return Single.defer(() -> {
            if (!connectivityManager.isConnected()) {
                return Single.error(new NoConnectivityException());
            }
            return source;
        });
    }

}