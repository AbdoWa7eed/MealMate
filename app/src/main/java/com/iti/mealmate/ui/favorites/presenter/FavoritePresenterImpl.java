package com.iti.mealmate.ui.favorites.presenter;


import android.util.Log;

import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.favorite.FavoriteRepository;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.ui.favorites.FavoritePresenter;
import com.iti.mealmate.ui.favorites.FavoriteView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImpl implements FavoritePresenter {

    private final FavoriteView view;
    private final FavoriteRepository favoriteRepository;

    private final PreferencesHelper preferencesHelper;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FavoritePresenterImpl(FavoriteView view, FavoriteRepository favoriteRepository,
                                 PreferencesHelper preferencesHelper) {
        this.view = view;
        this.favoriteRepository = favoriteRepository;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void onViewCreated() {
        loadFavorites();
    }

    @Override
    public void loadFavorites() {
        String uid = preferencesHelper.getUserId();
        if(uid == null) {
            view.showGuestMode();
            return;
        }
        view.showLoading();
        disposables.add(favoriteRepository.getAllFavorites(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess,
                        throwable -> {
                            view.hideLoading();
                            view.showPageError(throwable.getMessage());
                        }
                ));
    }

    private void onLoadSuccess(List<Meal> favorites) {
        if (favorites.isEmpty()) {
            view.showEmptyState();
        } else {
            view.showFavorites(favorites);
        }
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
    }

    private void onFavoriteRemoveError(Meal meal, Throwable throwable) {
        meal.setFavorite(true);
        view.showErrorMessage(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
