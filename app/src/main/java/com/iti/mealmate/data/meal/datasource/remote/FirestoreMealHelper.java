package com.iti.mealmate.data.meal.datasource.remote;

import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.mealmate.data.meal.exceptions.MealNotFoundException;
import com.iti.mealmate.data.meal.model.response.MealOfTheDay;

import java.time.LocalDate;

import io.reactivex.rxjava3.core.Single;

public class FirestoreMealHelper {

    private final FirebaseFirestore firestore;

    private static final String DAILY_MEALS = "dailyMeals";


    public FirestoreMealHelper(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }


    public Single<Void> saveMealOfTheDay(String mealId) {
        MealOfTheDay meal = new MealOfTheDay(mealId, LocalDate.now().toString());

        return Single.create(emitter ->
                firestore.collection(DAILY_MEALS)
                        .document(meal.getDate())
                        .set(meal)
                        .addOnSuccessListener(emitter::onSuccess)
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<MealOfTheDay> getMealOfTheDay() {
        String docId = LocalDate.now().toString();
        return Single.create(emitter ->
                firestore.collection(DAILY_MEALS)
                        .document(docId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                MealOfTheDay meal = documentSnapshot.toObject(MealOfTheDay.class);
                                if (meal != null) {
                                    emitter.onSuccess(meal);
                                } else {
                                    emitter.onError(new MealNotFoundException("Meal data is null"));
                                }
                            } else {
                                emitter.onError(new MealNotFoundException("No meal saved for today"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }


}
