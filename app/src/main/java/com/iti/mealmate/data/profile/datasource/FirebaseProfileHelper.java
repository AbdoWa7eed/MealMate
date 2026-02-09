package com.iti.mealmate.data.profile.datasource;

import android.net.Uri;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Single;

public class FirebaseProfileHelper {

    private static final String USERS_COLLECTION = "users";

    private final FirebaseFirestore firestore;
    private final FirebaseStorage storage;

    public FirebaseProfileHelper(FirebaseFirestore firestore,
                                 FirebaseStorage storage) {
        this.firestore = firestore;
        this.storage = storage;
    }

    public Single<UserModel> getUserProfile(String uid) {
        return RxTask.firebaseToSingleTask(
                firestore.collection(USERS_COLLECTION)
                        .document(uid)
                        .get()
        ).map(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                return documentSnapshot.toObject(UserModel.class);
            } else {
                throw new Exception("User profile not found");
            }
        });
    }

    public Single<UserModel> updateUserProfile(UserModel userModel, Uri imageUri) {

        if (imageUri == null) {
            return updateUserData(userModel);
        }

        return uploadProfileImage(userModel.getUid(), imageUri)
                .flatMap(imageUrl -> {
                    userModel.setImageUrl(imageUrl);
                    return updateUserData(userModel);
                });
    }

    public Single<String> updateProfileImage(String uid, Uri imageUri) {

        return uploadProfileImage(uid, imageUri)
                .flatMap(imageUrl ->
                        RxTask.firebaseToSingleTask(
                                firestore.collection(USERS_COLLECTION)
                                        .document(uid)
                                        .update("imageUrl", imageUrl)
                        ).map(v -> imageUrl)
                );
    }

    private Single<String> uploadProfileImage(String uid, Uri imageUri) {

        StorageReference imageRef = storage.getReference()
                .child("profile_images")
                .child(uid + ".jpg");

        return RxTask.firebaseToSingleTask(imageRef.putFile(imageUri))
                .flatMap(task ->
                        RxTask.firebaseToSingleTask(imageRef.getDownloadUrl()))
                .map(Uri::toString);
    }

    private Single<UserModel> updateUserData(UserModel userModel) {

        return RxTask.firebaseToSingleTask(
                firestore.collection(USERS_COLLECTION)
                        .document(userModel.getUid())
                        .set(userModel)
        ).map(v -> userModel);
    }
}
