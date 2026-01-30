package com.iti.mealmate.ui.splash;

import com.iti.mealmate.data.repo.AppStartupRepository;

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View view;
    private final AppStartupRepository appStartupRepository;

    public SplashPresenter(SplashContract.View view, AppStartupRepository appStartupRepository) {
        this.view = view;
        this.appStartupRepository = appStartupRepository;
    }

    @Override
    public void onViewCreated() {
        view.startSplashAnimations();
    }

    @Override
    public void onSplashTimeout() {
        // TODO: Navigate to Home/Login after onboarding is done
        if (appStartupRepository.isOnboardingCompleted()) {
            view.navigateToLogin();
        } else {
            view.navigateToOnboarding();
        }
    }

    @Override
    public void onDestroy() {}
}
