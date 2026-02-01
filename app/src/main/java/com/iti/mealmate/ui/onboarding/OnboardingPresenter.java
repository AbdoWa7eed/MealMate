package com.iti.mealmate.ui.onboarding;
import com.iti.mealmate.R;
import com.iti.mealmate.data.prefs.PreferencesHelper;
import com.iti.mealmate.ui.onboarding.model.OnboardingPage;

import java.util.ArrayList;
import java.util.List;

public class OnboardingPresenter implements OnboardingContract.Presenter {

    private final OnboardingContract.View view;
    private final PreferencesHelper preferencesHelper;
    private List<OnboardingPage> onboardingPages;


    public OnboardingPresenter(OnboardingContract.View view, PreferencesHelper preferencesHelper) {
        this.view = view;
        this.preferencesHelper = preferencesHelper;
        initializePages();
    }

    private void initializePages() {
        onboardingPages = new ArrayList<>();
        onboardingPages.add(new OnboardingPage(
                R.drawable.onboarding_image_1,
                R.string.onboarding_title_1,
                R.string.onboarding_description_1
        ));
        onboardingPages.add(new OnboardingPage(
                R.drawable.onboarding_image_2,
                R.string.onboarding_title_2,
                R.string.onboarding_description_2
        ));
        onboardingPages.add(new OnboardingPage(
                R.drawable.onboarding_image_3,
                R.string.onboarding_title_3,
                R.string.onboarding_description_3
        ));
    }

    @Override
    public void onViewCreated() {
        view.setupViewPager(onboardingPages);
        view.updateButtonText(R.string.next);
    }

    @Override
    public void onNextClicked(int currentPage) {
        if (currentPage < onboardingPages.size() - 1) {
            view.navigateToNextPage();
        } else {
            completeOnboarding();
        }
    }

    @Override
    public void onPageChanged(int position) {
        if (position == onboardingPages.size() - 1) {
            view.setSkipButtonVisible(false);
            view.updateButtonText(R.string.get_started);
        } else {
            view.updateButtonText(R.string.next);
        }
    }

    @Override
    public void completeOnboarding() {
        preferencesHelper.setOnboardingCompleted();
        view.navigateToLogin();
    }

    @Override
    public void onDestroy() {}
}