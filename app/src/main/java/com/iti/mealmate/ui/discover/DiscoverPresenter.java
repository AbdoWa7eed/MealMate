package com.iti.mealmate.ui.discover;

import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.data.filter.model.entity.FilterType;

import io.reactivex.rxjava3.core.Observable;

public interface DiscoverPresenter extends BasePresenter {
    void loadInitialFilters();
    void onFilterTypeSelected(FilterType type);
    void setupSearchObservable(Observable<String> searchObservable);

}

