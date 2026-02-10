package com.iti.mealmate.data.source.remote.firebase;

import android.net.Uri;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.auth.model.UserModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreUserHelper {

    private final FirebaseFirestore firestore;
    private final FirebaseStorage storage;
    private static final String USERS_COLLECTION = "users";

    public FirestoreUserHelper(FirebaseFirestore firestore, FirebaseStorage storage) {
        this.firestore = firestore;
        this.storage = storage;
    }

    public Single<UserModel> getUser(String uid) {
        return Single.create(emitter ->
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                UserModel user = documentSnapshot.toObject(UserModel.class);
                                if (user != null) emitter.onSuccess(user);
                                else emitter.onError(new Exception("Failed to parse user data"));
                            } else emitter.onError(new Exception("User not found"));
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

    public Single<String> updateProfileImage(String uid, Uri imageUri) {
        return uploadProfileImage(uid, imageUri)
                .flatMap(imageUrl ->
                        RxTask.firebaseVoidToCompletable(
                                firestore.collection(USERS_COLLECTION)
                                        .document(uid)
                                        .update("imageUrl", imageUrl)
                        ).toSingle(() -> imageUrl)
                );
    }

    public Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri) {
        if (imageUri == null) return saveUser(userModel).toSingle(() -> userModel);

        return uploadProfileImage(userModel.getUid(), imageUri)
                .flatMap(imageUrl -> {
                    userModel.setImageUrl(imageUrl);
                    return saveUser(userModel).toSingle(() -> userModel);
                });
    }

    private Single<String> uploadProfileImage(String uid, Uri imageUri) {
        StorageReference imageRef = storage.getReference()
                .child("profile_images")
                .child(uid + ".jpg");

        return RxTask.firebaseToSingleTask(imageRef.putFile(imageUri))
                .flatMap(task -> RxTask.firebaseToSingleTask(imageRef.getDownloadUrl()))
                .map(Uri::toString);
    }
}
