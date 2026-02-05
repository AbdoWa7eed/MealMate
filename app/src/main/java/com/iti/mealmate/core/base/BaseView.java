package com.iti.mealmate.core.base;

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void noInternetError();
}
