package com.iti.mealmate.core.util;

import com.google.android.gms.tasks.Task;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.network.NoConnectivityException;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class RxTask {

    public static <T> Single<T> firebaseToSingleTask(Task<T> task) {
        return Single.create(emitter ->
                task.addOnSuccessListener(emitter::onSuccess)
                        .addOnFailureListener(emitter::onError)
        );
    }

    public static Completable firebaseVoidToCompletable(Task<Void> task) {
        return Completable.create(emitter ->
                task.addOnSuccessListener(v -> emitter.onComplete())
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