package com.iti.mealmate.base;

public interface BasePresenter {
    default void onViewCreated() {}
    default void onDestroy() {}
}
