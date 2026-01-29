package com.iti.mealmate.ui.onboarding;

import com.iti.mealmate.base.BasePresenter;
import com.iti.mealmate.ui.onboarding.model.OnboardingPage;

import java.util.List;

public interface OnboardingContract {

    interface View {
        void setupViewPager(List<OnboardingPage> pages);
        void navigateToNextPage();
        void navigateToLogin();
        void updateButtonText(int stringResId);
        void setSkipButtonVisible(boolean visible);
    }

    interface Presenter extends BasePresenter {
        void onNextClicked(int currentPage);
        void onPageChanged(int position);
        OnboardingPage getPageData(int position);
        int getPageCount();
        void completeOnboarding();
    }

    interface PageView {
        void displayPageData(OnboardingPage page);
    }

    interface PagePresenter {
        void bindView(OnboardingContract.PageView view, int position);
    }
}