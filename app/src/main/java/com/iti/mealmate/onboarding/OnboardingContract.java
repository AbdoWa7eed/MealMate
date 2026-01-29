package com.iti.mealmate.onboarding;

import com.iti.mealmate.onboarding.model.OnboardingPage;

import java.util.List;

public interface OnboardingContract {

    interface View {
        void setupViewPager(List<OnboardingPage> pages);
        void navigateToNextPage();
        void navigateToMainActivity();
        void updateButtonText(int stringResId);
        void setSkipButtonVisible(boolean visible);
    }

    interface Presenter {
        void onViewCreated();
        void onNextClicked(int currentPage);
        void onPageChanged(int position);
        OnboardingPage getPageData(int position);
        int getPageCount();
        void onSkipClicked();
    }

    interface PageView {
        void displayPageData(OnboardingPage page);
    }

    interface PagePresenter {
        void bindView(OnboardingContract.PageView view, int position);
    }
}