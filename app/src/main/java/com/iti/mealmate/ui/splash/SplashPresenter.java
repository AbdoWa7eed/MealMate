package com.iti.mealmate.ui.splash;

import com.iti.mealmate.data.prefs.PreferencesHelper;

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View view;
    private final PreferencesHelper preferencesHelper;

    public SplashPresenter(SplashContract.View view, PreferencesHelper preferencesHelper) {
        this.view = view;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void onViewCreated() {
        view.startSplashAnimations();
    }

    @Override
    public void onSplashTimeout() {
        // TODO: Navigate to Home/Login after onboarding is done
        if (preferencesHelper.isOnboardingCompleted()) {
            view.navigateToLogin();
        } else {
            view.navigateToOnboarding();
        }
    }

    @Override
    public void onDestroy() {}
}
