package com.iti.mealmate.ui.splash.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.ActivitySplashBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.onboarding.view.OnboardingActivity;
import com.iti.mealmate.ui.splash.SplashContract;
import com.iti.mealmate.ui.splash.SplashPresenter;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private static final int SPLASH_DELAY_MS = 2500;

    private ActivitySplashBinding binding;
    private SplashContract.Presenter presenter;
    private Handler handler;
    private Runnable navigationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new SplashPresenter(this, ServiceLocator.getAppStartupRepository());
        presenter.onViewCreated();
        scheduleNavigation();
    }

    @Override
    public void startSplashAnimations() {
        Animation cardAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_card_scale);
        binding.imageCard.startAnimation(cardAnimation);

        AnimatedVectorDrawable animatedDrawable =
                (AnimatedVectorDrawable) ContextCompat.getDrawable(this, R.drawable.logo_animated);
        binding.logoImage.setImageDrawable(animatedDrawable);
        if (animatedDrawable != null) {
            animatedDrawable.start();
        }

        Animation titleAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_title_slide);
        binding.tvTitle.startAnimation(titleAnimation);

        Animation sloganAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slogan_slide);
        binding.tvSlogan.startAnimation(sloganAnimation);
    }

    private void scheduleNavigation() {
        handler = new Handler(Looper.getMainLooper());
        navigationRunnable = presenter::onSplashTimeout;
        handler.postDelayed(navigationRunnable, SPLASH_DELAY_MS);
    }

    @Override
    public void navigateToOnboarding() {
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

    @Override
    public void navigateToMain() {
        // TODO: Navigate to Home/Login after onboarding is done
        // For now, staying consistent with your determineDestination logic
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

    @Override
    public void navigateToLogin() {
        // TODO: Navigate to Login after onboarding is done
        startActivity(new Intent(this, OnboardingActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && navigationRunnable != null) {
            handler.removeCallbacks(navigationRunnable);
        }
        presenter.onDestroy();
        binding = null;
    }
}
