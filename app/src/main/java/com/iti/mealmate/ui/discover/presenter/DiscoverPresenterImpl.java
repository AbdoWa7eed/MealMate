package com.iti.mealmate.ui.discover.presenter;

import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.data.filter.model.entity.FilterItem;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.data.filter.repo.FilterRepository;
import com.iti.mealmate.ui.discover.DiscoverPresenter;
import com.iti.mealmate.ui.discover.DiscoverView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DiscoverPresenterImpl implements DiscoverPresenter {

    private final DiscoverView view;
    private final FilterRepository filterRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private List<FilterItem> categories;
    private List<FilterItem> countries;
    private List<FilterItem> ingredients;
    private FilterType currentType = FilterType.CATEGORY;
    private static final int INGREDIENT_PAGE_SIZE = 20;

    private Disposable currentFilterDisposable;


    public DiscoverPresenterImpl(DiscoverView view, FilterRepository filterRepository) {
        this.view = view;
        this.filterRepository = filterRepository;
    }

    @Override
    public void loadInitialFilters() {
        currentType = FilterType.CATEGORY;
        if (areFiltersCached()) {
            applyFilterAndShow();
            return;
        }

        view.showLoading();
        fetchAllFilters();
    }

    private boolean areFiltersCached() {
        return categories != null && countries != null && ingredients != null;
    }

    private void fetchAllFilters() {
        var filtersRequest = Single.zip(
                        filterRepository.getCategoryFilters().subscribeOn(Schedulers.io()),
                        filterRepository.getCountryFilters().subscribeOn(Schedulers.io()),
                        filterRepository.getIngredientFilters().subscribeOn(Schedulers.io()),
                        this::handleFiltersResult)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .subscribe(result -> applyFilterAndShow(), this::handleFiltersError);

        disposables.add(filtersRequest);
    }

    private Boolean handleFiltersResult(List<FilterItem> cat, List<FilterItem> country, List<FilterItem> ing) {
        categories = cat;
        countries = country;
        ingredients = ing;
        return true;
    }

    private void handleFiltersError(Throwable error) {
        if (!disposables.isDisposed()) {
            if (error instanceof NoConnectivityException) {
                view.noInternetError();
            } else {
                view.showError(error.getMessage());
            }
        }
    }

    @Override
    public void onFilterTypeSelected(FilterType type) {
        currentType = type;
        applyFilterAndShow();
    }

    @Override
    public void setupSearchObservable(Observable<String> searchObservable) {
        var searchDisposable = searchObservable
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(query -> filterObservable(query, currentType))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateUIWithFilteredList,
                        throwable -> view.showError(throwable.getMessage())
                );

        disposables.add(searchDisposable);
    }

    private Observable<List<FilterItem>> filterObservable(String query, FilterType type) {
        return Observable.fromCallable(() -> {
            List<FilterItem> baseList = getCachedListForType(type);
            if (baseList == null || baseList.isEmpty()) {
                return new ArrayList<FilterItem>();
            }
            return filterList(baseList, query, type);
        }).subscribeOn(Schedulers.computation());
    }

    private void applyFilterAndShow() {
        List<FilterItem> baseList = getCachedListForType(currentType);
        if (baseList == null) {
            return;
        }
        if (baseList.isEmpty()) {
            showEmptyList();
            return;
        }

        if (currentFilterDisposable != null && !currentFilterDisposable.isDisposed()) {
            currentFilterDisposable.dispose();
        }

        currentFilterDisposable =
                Observable.fromCallable(() -> filterList(baseList, "", currentType))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::updateUIWithFilteredList,
                                throwable -> view.showError(throwable.getMessage())
                        );

        disposables.add(currentFilterDisposable);
    }

    private List<FilterItem> filterList(List<FilterItem> baseList, String query, FilterType type) {
        return baseList.stream()
                .filter(item -> query.isEmpty() ||
                        (item.getName() != null && item.getName().toLowerCase().contains(query.toLowerCase())))
                .limit(type == FilterType.INGREDIENT && query.isEmpty() ? INGREDIENT_PAGE_SIZE : baseList.size())
                .collect(Collectors.toList());
    }

    private void updateUIWithFilteredList(List<FilterItem> filteredList) {
        if (filteredList.isEmpty()) view.showEmptyState();
        else view.hideEmptyState();
        view.showFilters(filteredList);
    }

    private void showEmptyList() {
        view.showEmptyState();
        view.showFilters(new ArrayList<>());
    }

    private List<FilterItem> getCachedListForType(FilterType type) {
        if (type == FilterType.COUNTRY) {
            return countries;
        }
        if (type == FilterType.INGREDIENT) {
            return ingredients;
        }
        if (type == FilterType.CATEGORY) {
            return categories;
        }

        return null;
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }
}