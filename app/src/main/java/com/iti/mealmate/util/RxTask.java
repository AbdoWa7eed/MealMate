package com.iti.mealmate.util;

import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.core.Single;

public class RxTask {

    public static <T> Single<T> firebaseToSingleTask(Task<T> task) {
        return Single.create(emitter ->
                task.addOnSuccessListener(emitter::onSuccess)
                        .addOnFailureListener(emitter::onError)
        );
    }

}