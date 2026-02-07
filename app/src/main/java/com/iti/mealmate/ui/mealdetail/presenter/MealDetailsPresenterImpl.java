package com.iti.mealmate.ui.mealdetail.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.ui.mealdetail.MealDetailsPresenter;
import com.iti.mealmate.ui.mealdetail.MealDetailsView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {

    private final MealDetailsView view;
    private final MealRepository mealRepository;
    private final FavoriteRepository favoriteRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private Meal meal;
    private String pendingMealId;

    public MealDetailsPresenterImpl(MealDetailsView view, MealRepository mealRepository, FavoriteRepository favoriteRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void handleIntent(Meal meal, String mealId) {
        if (meal != null) {
            setMeal(meal);
        } else if (mealId != null) {
            loadMealById(mealId);
        }
    }

    private void setMeal(Meal meal) {
        this.meal = meal;
        view.showContent();
        displayMealDetails(meal);
    }

    private void loadMealById(String mealId) {
        this.pendingMealId = mealId;
        view.showLoading();

        var loadMealRequest = mealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fetchedMeal -> {
                            this.meal = fetchedMeal;
                            view.hideLoading();
                            view.showContent();
                            displayMealDetails(fetchedMeal);
                        },
                        this::handleError
                );

        disposables.add(loadMealRequest);
    }

    @Override
    public void retry() {
        if (pendingMealId != null) {
            loadMealById(pendingMealId);
        }
    }

    private void handleError(Throwable throwable) {
        view.hideLoading();
        if (throwable instanceof NoConnectivityException) {
            view.noInternetError();
        } else {
            view.showPageError(throwable.getMessage());
        }
    }

    private void displayMealDetails(Meal meal) {
        displayMealSimpleDetails(meal);
        displayIngredients(meal);
        displayInstructions(meal);
    }

    private void displayMealSimpleDetails(Meal meal) {
        view.showMealName(meal.getName());
        view.showCountry(meal.getArea());
        view.showMealImage(meal.getThumbnailUrl());
        view.showFavoriteStatus(meal.isFavorite());
    }

    private void displayIngredients(Meal meal) {
        List<MealIngredient> ingredients = meal.getIngredients();
        if (ingredients != null) {
            view.showItemsCount(ingredients.size());
            view.showIngredients(ingredients);
        } else {
            view.showItemsCount(0);
        }
    }

    private void displayInstructions(Meal meal) {
        if (meal.getInstructions() != null && !meal.getInstructions().isEmpty()) {
            String[] stepsArray = meal.getInstructions().split("\\r?\\n");
            List<String> steps = new ArrayList<>();

            for (String step : stepsArray) {
                step = step.trim();
                if (step.isEmpty()) continue;

                if (step.toLowerCase().matches("^step\\s+\\d+.*")) continue;
                if (step.matches("^\\d+\\..*")) continue;
                steps.add(step);
            }

            view.showPreparationSteps(steps);
        }
    }

    @Override
    public void addToPlan() {
    }

    @Override
    public void onVideoClicked() {
        view.showVideoLoading();
        view.showVideo(meal.getYoutubeUrl());
    }

    @Override
    public void toggleFavorite() {
        if (meal == null) return;
        
        if (meal.isFavorite()) {
            disposables.add(favoriteRepository.removeFromFavorites(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                meal.setFavorite(false);
                                view.showFavoriteStatus(false);
                                view.showSuccessMessage("Removed from favorites");
                            },
                            throwable -> view.showErrorMessage(throwable.getMessage())
                    ));
        } else {
            disposables.add(favoriteRepository.addToFavorites(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                meal.setFavorite(true);
                                view.showFavoriteStatus(true);
                                view.showSuccessMessage("Added to favorites");
                            },
                            throwable -> view.showErrorMessage(throwable.getMessage())
                    ));
        }
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
