package com.iti.mealmate.core.base;

public interface BasePresenter {
    default void onViewCreated() {}
    default void onDestroy() {}
}
