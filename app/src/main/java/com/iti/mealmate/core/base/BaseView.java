package com.iti.mealmate.core.base;

public interface BaseView {
    void showLoading();
    void hideLoading();
    default void showPageError(String message) {}

    default void showErrorMessage(String message) {}

    default void showSuccessMessage(String message) {}

    default void noInternetError() {}
}
