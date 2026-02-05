package com.iti.mealmate.ui.mealdetail.view;

import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.mealmate.databinding.ActivityMealDetailsBinding;
import com.iti.mealmate.ui.common.ErrorOverlayHelper;

public class MealDetailsUiStateHandler {

    private final ActivityMealDetailsBinding binding;

    public MealDetailsUiStateHandler(ActivityMealDetailsBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        hideContent();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        setToolbarItemsColor(android.R.color.white);
        binding.btnFavorite.setVisibility(View.GONE);
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    public void hideLoading() {
        stopAndHideLoading();
    }

    public void showContent() {
        stopAndHideLoading();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        setToolbarItemsColor(android.R.color.white);
        binding.btnFavorite.setVisibility(View.VISIBLE);
        binding.contentContainer.setVisibility(View.VISIBLE);
        binding.bottomBar.setVisibility(View.VISIBLE);
    }

    public void showError(String message, Runnable retryAction) {
        stopAndHideLoading();
        hideContent();
        setToolbarItemsColor(android.R.color.black);
        binding.btnFavorite.setVisibility(View.GONE);
        ErrorOverlayHelper.showError(binding.errorOverlay.getRoot(), message, retryAction);
    }

    public void showNoInternetError(Runnable retryAction) {
        stopAndHideLoading();
        hideContent();
        setToolbarItemsColor(android.R.color.black);
        binding.btnFavorite.setVisibility(View.GONE);
        ErrorOverlayHelper.showNetworkError(binding.errorOverlay.getRoot(), retryAction);
    }

    private void hideContent() {
        binding.contentContainer.setVisibility(View.GONE);
        binding.bottomBar.setVisibility(View.GONE);
    }

    private void stopAndHideLoading() {
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.stopShimmer();
        shimmer.setVisibility(View.GONE);
    }

    private void setToolbarItemsColor(int colorRes) {
        int color = binding.getRoot().getContext().getColor(colorRes);
        binding.btnBack.setColorFilter(color);
        binding.btnFavorite.setColorFilter(color);
    }
}
