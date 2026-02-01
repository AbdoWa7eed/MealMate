package com.iti.mealmate.ui.onboarding.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.iti.mealmate.databinding.ActivityOnboardingBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.auth.AuthenticationActivity;
import com.iti.mealmate.ui.onboarding.OnboardingContract;
import com.iti.mealmate.ui.onboarding.OnboardingPresenter;
import com.iti.mealmate.ui.onboarding.model.OnboardingPage;
import com.iti.mealmate.ui.utils.ActivityExtensions;

import java.util.List;

public class OnboardingActivity extends AppCompatActivity implements OnboardingContract.View {

    private ActivityOnboardingBinding binding;
    private OnboardingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
        initializePresenter();
        setupListeners();
        presenter.onViewCreated();
    }

    private void initializeUI() {
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ActivityExtensions.setStatusBarWithDarkIcons(this);
    }

    private void initializePresenter() {
        presenter = new OnboardingPresenter(
                this,
                ServiceLocator.getAppStartupRepository()
        );
    }

    private void setupListeners() {
        setupButtonsClicks();
        setupPageChangeListener();
    }

    private void setupButtonsClicks() {
        binding.btnNext.setOnClickListener(v -> {
            int currentPage = binding.viewPager.getCurrentItem();
            presenter.onNextClicked(currentPage);
        });

        binding.btnSkip.setOnClickListener(v -> presenter.completeOnboarding());
    }

    private void setupPageChangeListener() {
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                presenter.onPageChanged(position);
            }
        });
    }

    @Override
    public void setupViewPager(List<OnboardingPage> pages) {
        OnboardingAdapter adapter = new OnboardingAdapter(this, pages);
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.attachTo(binding.viewPager);
    }

    @Override
    public void navigateToNextPage() {
        int nextItem = binding.viewPager.getCurrentItem() + 1;
        binding.viewPager.setCurrentItem(nextItem, true);
    }

    @Override
    public void navigateToLogin() {
        ActivityExtensions.navigateToActivity(this, AuthenticationActivity.class);
        finish();
    }

    @Override
    public void updateButtonText(int stringResId) {
        binding.btnNext.setText(stringResId);
    }

    @Override
    public void setSkipButtonVisible(boolean visible) {
        binding.btnSkip.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}