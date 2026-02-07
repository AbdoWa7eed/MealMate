package com.iti.mealmate.ui.favorites.presenter;


import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.ui.favorites.FavoritePresenter;
import com.iti.mealmate.ui.favorites.FavoriteView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
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
                            loadFavorites();
                            view.showErrorMessage(throwable.getMessage());
                        }
                ));
    }

    private void onFavoriteToggled(Meal meal, boolean isFavorite) {
        meal.setFavorite(isFavorite);
        view.showSuccessMessage(isFavorite ? "Added to favorites" : "Removed from favorites");
        loadFavorites();
    }


    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
