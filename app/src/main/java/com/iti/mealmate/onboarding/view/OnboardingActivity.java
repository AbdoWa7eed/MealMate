package com.iti.mealmate.onboarding.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.iti.mealmate.databinding.ActivityOnboardingBinding;
import com.iti.mealmate.onboarding.OnboardingContract;
import com.iti.mealmate.onboarding.OnboardingPresenter;
import com.iti.mealmate.onboarding.model.OnboardingPage;
import com.iti.mealmate.utils.ActivityExtensions;

import java.util.List;

public class OnboardingActivity extends AppCompatActivity implements OnboardingContract.View {

    private ActivityOnboardingBinding binding;
    private OnboardingPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ActivityExtensions.setStatusBarColor(this, android.R.color.white, true);
        presenter = new OnboardingPresenter(this);
        presenter.onViewCreated();
        setupButtonsClicks();
        setupPageChangeListener();
    }

    @Override
    public void setupViewPager(List<OnboardingPage> pages) {
        OnboardingAdapter adapter =
                new OnboardingAdapter(this, presenter.getPageCount(), presenter);
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.attachTo(binding.viewPager);
    }

    private void setupButtonsClicks() {
        binding.btnNext.setOnClickListener(v -> {
            int currentPage = binding.viewPager.getCurrentItem();
            presenter.onNextClicked(currentPage);
        });

        binding.btnSkip.setOnClickListener(v -> {
            presenter.onSkipClicked();
        });
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
    public void navigateToNextPage() {
        int nextItem = binding.viewPager.getCurrentItem() + 1;
        binding.viewPager.setCurrentItem(nextItem, true);
    }

    @Override
    public void navigateToMainActivity() {
        finish();
    }

    @Override
    public void updateButtonText(int stringResId) {
        binding.btnNext.setText(getString(stringResId));
    }

    @Override
    public void setSkipButtonVisible(boolean visible) {
        binding.btnSkip.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}