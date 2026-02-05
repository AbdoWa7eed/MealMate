package com.iti.mealmate.ui.meallist.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.data.meal.repo.MealRepository;
import com.iti.mealmate.ui.common.MealListArgs;
import com.iti.mealmate.ui.meallist.MealListPresenter;
import com.iti.mealmate.ui.meallist.MealListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealListPresenterImpl implements MealListPresenter {

    private final MealListView view;
    private final MealRepository mealRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private List<MealLight> cachedMeals = Collections.emptyList();
    private MealListArgs lastArgs;
    private Disposable searchDisposable;


    public MealListPresenterImpl(MealListView view, MealRepository mealRepository) {
        this.view = view;
        this.mealRepository = mealRepository;
    }

    @Override
    public void loadList(MealListArgs args) {
        if (args == null || args.getQuery() == null || args.getQuery().isEmpty()) {
            view.showEmptyState();
            return;
        }

        lastArgs = args;
        FilterType type = args.getFilterType();

        if (type == null) {
            view.showEmptyState();
            return;
        }

        if (type == FilterType.CATEGORY) {
            loadMeals(mealRepository.getMealsByCategory(args.getQuery()));
        } else if (type == FilterType.COUNTRY) {
            loadMeals(mealRepository.getMealsByCountry(args.getQuery()));
        } else if (type == FilterType.INGREDIENT) {
            loadMeals(mealRepository.getMealsByIngredient(args.getQuery()));
        } else {
            loadMeals(mealRepository.searchMealsByName(args.getQuery()));
        }
    }

    public void retry() {
        if (lastArgs != null) {
            loadList(lastArgs);
        }
    }

    @Override
    public void setupSearchObservable(Observable<String> searchObservable) {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }

        searchDisposable = searchObservable
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSearchQuery);
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }

    private void loadMeals(Single<List<MealLight>> source) {
        view.showLoading();

        var disposable = source
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(
                        this::handleMealsSuccess,
                        this::handleMealsError
                );

        disposables.add(disposable);
    }

    private void handleMealsSuccess(List<MealLight> meals) {
        cachedMeals = meals != null ? meals : Collections.emptyList();

        if (meals == null || meals.isEmpty()) {
            view.showEmptyState();
        } else {
            view.hideEmptyState();
            view.showMeals(meals);
        }
    }

    private void handleMealsError(Throwable error) {
        if (!disposables.isDisposed()) {
            if (error instanceof NoConnectivityException) {
                view.noInternetError();
            } else {
                view.showError(error.getMessage());
            }
        }
    }

    private void handleSearchQuery(String query) {
        if (lastArgs == null) return;

        if (lastArgs.getFilterType() == FilterType.SEARCH) {
            loadList(MealListArgs.search(query));
        } else {
            searchLocal(query);
        }
    }

    public void searchLocal(String query) {
        if (query == null || query.isEmpty()) {
            view.showMeals(cachedMeals);
        } else {
            searchInLocal(query);
        }
    }

    private void searchInLocal(String query) {
        if (cachedMeals.isEmpty()) {
            view.showEmptyState();
            return;
        }

        String lowerQuery = query.toLowerCase();
        List<MealLight> filtered = new ArrayList<>();

        for (MealLight meal : cachedMeals) {
            if (meal.getName().toLowerCase().contains(lowerQuery)) {
                filtered.add(meal);
            }
        }

        if (filtered.isEmpty()) {
            view.showEmptyState();
        } else {
            view.hideEmptyState();
            view.showMeals(filtered);
        }
    }
}
