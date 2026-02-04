package com.iti.mealmate.ui.splash;

import com.iti.mealmate.base.BasePresenter;

public interface SplashContract {
    interface View {
        void navigateToOnboarding();
        void navigateToHome();
        void navigateToLogin();
        void startSplashAnimations();
    }

    interface Presenter extends BasePresenter {
        void onSplashTimeout();
    }
}
