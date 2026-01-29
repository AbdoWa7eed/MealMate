package com.iti.mealmate.onboarding.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.databinding.FragmentOnboardingPageBinding;
import com.iti.mealmate.onboarding.OnboardingContract;
import com.iti.mealmate.onboarding.model.OnboardingPage;

public class OnboardingPageFragment extends Fragment implements OnboardingContract.PageView {

    private FragmentOnboardingPageBinding binding;
    private OnboardingContract.PagePresenter presenter;
    private int position;

    private static final String ARG_POSITION = "position";

    public static OnboardingPageFragment newInstance(
            @NonNull OnboardingContract.PagePresenter presenter, int position) {
        OnboardingPageFragment fragment = new OnboardingPageFragment();
        fragment.presenter = presenter;
        fragment.saveStateToBundle(position);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
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
        presenter.bindView(this, position);
    }

    @Override
    public void displayPageData(OnboardingPage page) {
        binding.onboardingImage.setImageResource(page.getImageResId());
        binding.titleText.setText(getString(page.getTitleResId()));
        binding.subtitleText.setText(getString(page.getDescriptionResId()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void saveStateToBundle(int position){
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        setArguments(args);
    }
}
