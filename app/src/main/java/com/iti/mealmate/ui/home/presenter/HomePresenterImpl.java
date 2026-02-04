package com.iti.mealmate.ui.home.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.ui.home.HomePresenter;
import com.iti.mealmate.ui.home.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {

    private final HomeView view;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private HomeData data;

    public HomePresenterImpl(HomeView view, MealRepository mealRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
    }

    @Override
    public void loadHomeData() {
        if (data != null) {
             loadData(data);
            return;
        }

        view.showLoading();
        var homeRequest = Single.zip(
                        mealRepository.getMealOfTheDay(),
                        mealRepository.getSuggestedMeals(),
                        HomeData::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(this::loadData, error -> {
                    if (error instanceof NoConnectivityException) {
                        view.noInternetError();
                    } else {
                        view.showError(error.getMessage());
                    }
                });

        disposables.add(homeRequest);
    }

    private void loadData(HomeData data) {
        this.data = data;
        view.showMealOfTheDay(data.mealOfTheDay);
        view.showTrendingMeals(data.suggestedMeals);
    }


    @Override
    public void onDestroy() {
        disposables.clear();
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
