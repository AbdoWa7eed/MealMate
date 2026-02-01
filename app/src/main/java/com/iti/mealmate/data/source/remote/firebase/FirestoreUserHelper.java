package com.iti.mealmate.data.source.remote.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.mealmate.data.auth.model.UserModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreUserHelper {

    private final FirebaseFirestore firestore;
    private static final String USERS_COLLECTION = "users";

    public FirestoreUserHelper(FirebaseFirestore firebaseFirestore) {
        this.firestore = firebaseFirestore;
    }

    public Single<UserModel> getUser(String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                UserModel user = documentSnapshot.toObject(UserModel.class);
                                if (user != null) {
                                    emitter.onSuccess(user);
                                } else {
                                    emitter.onError(new Exception("Failed to parse user data"));
                                }
                            } else {
                                emitter.onError(new Exception("User not found"));
                            }
                        }).addOnFailureListener(emitter::onError)
        );
    }

    public Completable saveUser(UserModel user) {
        return Completable.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(user.getUid())
                        .set(user)
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Completable updateUser(UserModel user) {
        return Completable.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(user.getUid())
                        .update(
                                "name", user.getName(),
                                "email", user.getEmail(),
                                "imageUrl", user.getImageUrl()
                        )
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<Boolean> userExists(String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(doc -> emitter.onSuccess(doc.exists()))
                        .addOnFailureListener(emitter::onError)
        );
    }
}