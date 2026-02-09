package com.iti.mealmate.data.meal.datasource.remote;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.exceptions.MealNotFoundException;
import com.iti.mealmate.data.meal.model.response.MealOfTheDay;
import com.iti.mealmate.core.util.RxTask;

import io.reactivex.rxjava3.core.Single;

public class FirestoreMealHelper {

    private final FirebaseFirestore firestore;
    private static final String DAILY_MEALS = "dailyMeals";

    public FirestoreMealHelper(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public Single<MealOfTheDay> saveMealOfTheDay(MealOfTheDay mealOfTheDay) {
        String today = DateUtils.todayString();
        mealOfTheDay.setDate(today);

        return RxTask.firebaseToSingleTask(
                firestore.collection(DAILY_MEALS)
                        .document(today)
                        .set(mealOfTheDay)
        ).map(aVoid -> mealOfTheDay);
    }

    public Single<MealOfTheDay> getMealOfTheDay() {
        return getDocument(DateUtils.todayString())
                .flatMap(this::validateAndExtractMealOfTheDay);
    }

    private Single<DocumentSnapshot> getDocument(String date) {
        return RxTask.firebaseToSingleTask(
                firestore.collection(DAILY_MEALS)
                        .document(date)
                        .get()
        );
    }

    private Single<MealOfTheDay> validateAndExtractMealOfTheDay(DocumentSnapshot snapshot) {
        if (!snapshot.exists()) {
            return Single.error(new MealNotFoundException("No meals cached for today"));
        }

        MealOfTheDay meal = snapshot.toObject(MealOfTheDay.class);

        if (meal == null || meal.getDailyMeal() == null ||
                meal.getSuggestedMeals() == null || meal.getSuggestedMeals().isEmpty()) {
            return Single.error(new MealNotFoundException("Incomplete meal data"));
        }

        return Single.just(meal);
    }


}