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
    public void removeFavorite(Meal meal) {
        disposables.add(favoriteRepository.removeFromFavorites(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> onFavoriteRemoved(meal),
                        throwable -> onFavoriteRemoveError(meal, throwable)
                ));
    }

    private void onFavoriteRemoved(Meal meal) {
        meal.setFavorite(false);
        view.showSuccessMessage("Removed from favorites");
        loadFavorites();
    }

    private void onFavoriteRemoveError(Meal meal, Throwable throwable) {
        meal.setFavorite(true);
        loadFavorites();
        view.showErrorMessage(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
