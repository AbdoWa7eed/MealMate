package com.iti.mealmate.ui.meallist;


import com.iti.mealmate.core.base.BasePresenter;
import com.iti.mealmate.ui.common.MealListArgs;

import io.reactivex.rxjava3.core.Observable;

public interface MealListPresenter extends BasePresenter {

    void loadList(MealListArgs args);

    void retry();

    public void setupSearchObservable(Observable<String> searchObservable);
}


