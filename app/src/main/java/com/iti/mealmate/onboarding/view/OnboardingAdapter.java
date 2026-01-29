package com.iti.mealmate.onboarding.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.iti.mealmate.onboarding.OnboardingContract;


public class OnboardingAdapter extends FragmentStateAdapter {

    private final int pageCount;
    private final OnboardingContract.PagePresenter presenter;

    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity, int pageCount, OnboardingContract.PagePresenter presenter) {
        super(fragmentActivity);
        this.pageCount = pageCount;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboardingPageFragment.newInstance(presenter, position);
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }
}
