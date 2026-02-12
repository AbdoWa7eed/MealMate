package com.iti.mealmate.data.source.remote.firebase;

import android.net.Uri;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.Meal;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreUserHelper {

    private final FirebaseFirestore firestore;
    private final FirebaseStorage storage;

    private static final String USERS = "users";
    private static final String FAVORITES = "favorites";
    private static final String PLANS = "plan";
    private static final String PROFILE_IMAGES = "profile_images";
    private static final String IMG_EXT = ".jpg";

    public FirestoreUserHelper(FirebaseFirestore firestore, FirebaseStorage storage) {
        this.firestore = firestore;
        this.storage = storage;
    }

    public Single<UserModel> getUser(String uid) {
        return Single.create(emitter ->
                getUserDoc(uid).get()
                        .addOnSuccessListener(doc -> {
                            if (doc.exists()) {
                                UserModel user = doc.toObject(UserModel.class);
                                if (user != null) emitter.onSuccess(user);
                                else emitter.onError(new Exception("Failed to parse user"));
                            } else emitter.onError(new Exception("User not found"));
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Completable saveUser(UserModel user) {
        return RxTask.firebaseVoidToCompletable(getUserDoc(user.getUid()).set(user));
    }

    public Single<String> updateProfileImage(String uid, Uri imageUri) {
        return uploadProfileImage(uid, imageUri)
                .flatMap(url -> RxTask.firebaseVoidToCompletable(
                                getUserDoc(uid).update("imageUrl", url))
                        .toSingle(() -> url));
    }

    public Single<UserModel> updateUserProfile(UserModel user, Uri imageUri) {
        if (imageUri == null) return saveUser(user).toSingle(() -> user);
        return uploadProfileImage(user.getUid(), imageUri)
                .flatMap(url -> {
                    user.setImageUrl(url);
                    return saveUser(user).toSingle(() -> user);
                });
    }

    private Single<String> uploadProfileImage(String uid, Uri uri) {
        StorageReference ref = storage.getReference()
                .child(PROFILE_IMAGES)
                .child(uid + IMG_EXT);
        return RxTask.firebaseToSingleTask(ref.putFile(uri))
                .flatMap(t -> RxTask.firebaseToSingleTask(ref.getDownloadUrl()))
                .map(Uri::toString);
    }

    public Completable syncFavoriteMeals(String uid, List<Meal> favorites) {
        CollectionReference ref = getUserDoc(uid).collection(FAVORITES);
        List<String> ids = favorites.stream().map(Meal::getId).collect(Collectors.toList());

        return RxTask.firebaseToSingleTask(ref.get())
                .flatMapCompletable(qs -> {
                    List<Completable> deletes = qs.getDocuments().stream()
                            .filter(d -> !ids.contains(d.getId()))
                            .map(d -> RxTask.firebaseVoidToCompletable(d.getReference().delete()))
                            .collect(Collectors.toList());
                    List<Completable> uploads = favorites.stream()
                            .map(f -> RxTask.firebaseVoidToCompletable(ref.document(f.getId()).set(f)))
                            .collect(Collectors.toList());
                    return Completable.concat(deletes)
                            .andThen(Completable.concat(uploads))
                            .andThen(RxTask.firebaseVoidToCompletable(
                                    getUserDoc(uid).update("favoriteMealsCount", favorites.size(),
                                            "lastSyncedDate", System.currentTimeMillis())
                            ));
                });
    }

    public Single<List<Meal>> getFavoriteMeals(String uid) {
        return RxTask.firebaseToSingleTask(getUserDoc(uid).collection(FAVORITES).get())
                .map(qs -> qs.toObjects(Meal.class));
    }

    public Completable syncPlans(String uid, List<DayPlan> plans) {
        CollectionReference ref = getUserDoc(uid).collection(PLANS);
        List<String> ids = plans.stream().map(DayPlan::getDate).collect(Collectors.toList());
        int totalMeals = plans.stream().mapToInt(p -> p.getMeals().size()).sum();
        return RxTask.firebaseToSingleTask(ref.get())
                .flatMapCompletable(qs -> {
                    List<Completable> deletes = qs.getDocuments().stream()
                            .filter(d -> !ids.contains(d.getId()))
                            .map(d -> RxTask.firebaseVoidToCompletable(d.getReference().delete()))
                            .collect(Collectors.toList());
                    List<Completable> uploads = plans.stream()
                            .map(p -> RxTask.firebaseVoidToCompletable(ref.document(p.getDate()).set(p)))
                            .collect(Collectors.toList());
                    return Completable.concat(deletes)
                            .andThen(Completable.concat(uploads))
                            .andThen(RxTask.firebaseVoidToCompletable(
                                    getUserDoc(uid).update("plannedMealsCount", totalMeals,
                                            "lastSyncedDate", System.currentTimeMillis())
                            ));
                });
    }

    public Single<List<DayPlan>> getPlans(String uid) {
        return RxTask.firebaseToSingleTask(getUserDoc(uid).collection(PLANS).get())
                .map(qs -> qs.toObjects(DayPlan.class));
    }

    private com.google.firebase.firestore.DocumentReference getUserDoc(String uid) {
        return firestore.collection(USERS).document(uid);
    }
}
