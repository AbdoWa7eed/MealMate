package com.iti.mealmate.ui.onboarding;
import com.iti.mealmate.R;
import com.iti.mealmate.data.repo.AppStartupRepository;
import com.iti.mealmate.ui.onboarding.model.OnboardingPage;

import java.util.ArrayList;
import java.util.List;

public class OnboardingPresenter implements OnboardingContract.Presenter, OnboardingContract.PagePresenter {

    private final OnboardingContract.View view;
    private final AppStartupRepository appStartupRepository;
    private List<OnboardingPage> onboardingPages;


    public OnboardingPresenter(OnboardingContract.View view, AppStartupRepository appStartupRepository) {
        this.view = view;
        this.appStartupRepository = appStartupRepository;
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
    public OnboardingPage getPageData(int position) {
        if (position >= 0 && position < onboardingPages.size()) {
            return onboardingPages.get(position);
        }
        return null;
    }

    @Override
    public int getPageCount() {
        return onboardingPages.size();
    }

    @Override
    public void bindView(OnboardingContract.PageView pageView, int position) {
        OnboardingPage page = getPageData(position);
        if (page != null) {
            pageView.displayPageData(page);
        }
    }

    @Override
    public void completeOnboarding() {
        appStartupRepository.setOnboardingCompleted();
        view.navigateToLogin();
    }

    @Override
    public void onDestroy() {

    }
}