package com.iti.mealmate.ui.onboarding.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class OnboardingAdapter extends FragmentStateAdapter {

    private final int pageCount;

    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity, int pageCount) {
        super(fragmentActivity);
        this.pageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboardingPageFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }
}
