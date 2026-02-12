package com.iti.mealmate.ui.home.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.ui.home.HomePresenter;
import com.iti.mealmate.ui.home.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {

    private final HomeView view;
    private final MealRepository mealRepository;
    private final FavoriteRepository favoriteRepository;
    private final PreferencesHelper preferencesHelper;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private HomeData data;
    private Disposable currentHomeRequest;

    public HomePresenterImpl(HomeView view, MealRepository mealRepository, FavoriteRepository favoriteRepository, PreferencesHelper preferencesHelper) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void loadHomeData() {
        if(data != null)
            return;
        view.showLoading();
        currentHomeRequest = Flowable.zip(
                        mealRepository.getMealOfTheDay(),
                        mealRepository.getSuggestedMeals(),
                        HomeData::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadData, this::handleHomeError);

        disposables.add(currentHomeRequest);
    }

    private void handleHomeError(Throwable error) {
        view.hideLoading();
        if (!disposables.isDisposed()) {
            if (error instanceof NoConnectivityException) {
                view.noInternetError();
            } else {
                view.showPageError(error.getMessage());
            }
        }
    }

    private void loadData(HomeData data) {
        this.data = data;
        view.showMealOfTheDay(data.mealOfTheDay);
        view.showTrendingMeals(data.suggestedMeals);
        view.hideLoading();
    }

    @Override
    public void toggleFavorite(Meal meal) {
        if (preferencesHelper.getUserId() == null) {
            view.showErrorMessage("This action is not allowed for guest users");
            return;
        }
        boolean previousStatus = meal.isFavorite();
        Completable action = previousStatus
                ? favoriteRepository.removeFromFavorites(meal)
                : favoriteRepository.addToFavorites(meal);

        disposables.add(action
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> onFavoriteToggled(meal, !previousStatus),
                        throwable -> {
                            meal.setFavorite(previousStatus);
                            view.showTrendingMeals(data.suggestedMeals);
                            view.showErrorMessage(throwable.getMessage());
                        }
                ));
    }

    private void onFavoriteToggled(Meal meal, boolean isFavorite) {
        meal.setFavorite(isFavorite);
        view.showSuccessMessage(isFavorite ? "Added to favorites" : "Removed from favorites");
        view.showTrendingMeals(data.suggestedMeals);
    }


    @Override
    public void onDestroy() {
        disposables.clear();
        currentHomeRequest = null;
    }

    private static class HomeData {
        final Meal mealOfTheDay;
        final List<Meal> suggestedMeals;

        HomeData(Meal mealOfTheDay, List<Meal> suggestedMeals) {
            this.mealOfTheDay = mealOfTheDay;
            this.suggestedMeals = suggestedMeals;
        }
    }
}
