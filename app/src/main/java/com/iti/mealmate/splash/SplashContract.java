package com.iti.mealmate.splash;

import com.iti.mealmate.base.BasePresenter;

public interface SplashContract {
    interface View {
        void navigateToOnboarding();
        void navigateToMain();
        void navigateToLogin();
        void startSplashAnimations();
    }

    interface Presenter extends BasePresenter {
        void onSplashTimeout();
    }
}
