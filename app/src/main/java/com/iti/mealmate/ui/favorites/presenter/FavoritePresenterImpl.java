package com.iti.mealmate.ui.favorites.presenter;


import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.ui.favorites.FavoritePresenter;
import com.iti.mealmate.ui.favorites.FavoriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImpl implements FavoritePresenter {

    private final FavoriteView view;
    private final FavoriteRepository favoriteRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FavoritePresenterImpl(FavoriteView view, FavoriteRepository favoriteRepository) {
        this.view = view;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void onViewCreated() {
        loadFavorites();
    }

    @Override
    public void loadFavorites() {
        view.showLoading();
        disposables.add(favoriteRepository.getAllFavoriteIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favorites -> {
                            view.hideLoading();
                            if (favorites.isEmpty()) {
                                view.showEmptyState();
                            } else {
                                view.showFavorites(favorites);
                            }
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showPageError(throwable.getMessage());
                        }
                ));
    }

    @Override
    public void toggleFavorite(Meal meal) {
        if (meal.isFavorite()) {
            disposables.add(favoriteRepository.removeFromFavorites(meal.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                meal.setFavorite(false);
                                view.showSuccessMessage("Removed from favorites");
                                loadFavorites();
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
                                view.showSuccessMessage("Added to favorites");
                                loadFavorites();
                            },
                            throwable -> view.showErrorMessage(throwable.getMessage())
                    ));
        }
    }

    @Override
    public void onMealClicked(Meal meal) {
        view.navigateToDetails(meal);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
