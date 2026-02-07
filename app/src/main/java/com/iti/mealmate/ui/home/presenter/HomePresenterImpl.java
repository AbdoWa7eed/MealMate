package com.iti.mealmate.ui.home.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.ui.home.HomePresenter;
import com.iti.mealmate.ui.home.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {

    private final HomeView view;
    private final MealRepository mealRepository;
    private final FavoriteRepository favoriteRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private HomeData data;
    private Disposable currentHomeRequest;

    public HomePresenterImpl(HomeView view, MealRepository mealRepository, FavoriteRepository favoriteRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void loadHomeData() {
        if (data != null) {
            loadData(data);
            return;
        }

        view.showLoading();
        currentHomeRequest = Single.zip(
                        mealRepository.getMealOfTheDay(),
                        mealRepository.getSuggestedMeals(),
                        HomeData::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(this::loadData, this::handleHomeError);

        disposables.add(currentHomeRequest);
    }

    private void handleHomeError(Throwable error) {
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
    }

    @Override
    public void toggleFavorite(Meal meal) {
        Disposable request;
        if (meal.isFavorite()) {
            request = favoriteRepository.removeFromFavorites(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                meal.setFavorite(false);
                                view.showSuccessMessage("Removed from favorites");
                                view.showTrendingMeals(data.suggestedMeals);
                            },
                            throwable -> view.showErrorMessage(throwable.getMessage()));
        } else {
            request = favoriteRepository.addToFavorites(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                                meal.setFavorite(true);
                                view.showSuccessMessage("Added to favorites");
                                view.showTrendingMeals(data.suggestedMeals);
                            },
                            throwable -> view.showErrorMessage(throwable.getMessage())
                    );
        }
        disposables.add(request);
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
