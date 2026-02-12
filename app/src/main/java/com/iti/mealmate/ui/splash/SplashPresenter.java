package com.iti.mealmate.ui.splash;

import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View view;
    private final PreferencesHelper preferencesHelper;
    private final AuthRepository authRepository;

    public SplashPresenter(SplashContract.View view, PreferencesHelper preferencesHelper, AuthRepository authRepository) {
        this.view = view;
        this.preferencesHelper = preferencesHelper;
        this.authRepository = authRepository;
    }

    @Override
    public void onViewCreated() {
        view.startSplashAnimations();
    }

    @Override
    public void onSplashTimeout() {
        if(authRepository.isUserLoggedIn()){
            view.navigateToHome();
            return;
        }
        if (preferencesHelper.isOnboardingCompleted()) {
            view.navigateToLogin();
        } else {
            view.navigateToOnboarding();
        }
    }

}
