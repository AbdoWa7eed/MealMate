package com.iti.mealmate.ui.onboarding.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.databinding.FragmentOnboardingPageBinding;
import com.iti.mealmate.ui.onboarding.model.OnboardingPage;

public class OnboardingPageFragment extends Fragment {

    private FragmentOnboardingPageBinding binding;

    private static final String ARG_PAGE = "page";

    public static OnboardingPageFragment newInstance(OnboardingPage page) {
        OnboardingPageFragment fragment = new OnboardingPageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnboardingPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayPageData();
    }

    private void displayPageData() {
        if (getArguments() != null) {
            OnboardingPage page = (OnboardingPage) getArguments().getSerializable(ARG_PAGE);
            if (page != null) {
                binding.onboardingImage.setImageResource(page.getImageResId());
                binding.titleText.setText(getString(page.getTitleResId()));
                binding.subtitleText.setText(getString(page.getDescriptionResId()));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}