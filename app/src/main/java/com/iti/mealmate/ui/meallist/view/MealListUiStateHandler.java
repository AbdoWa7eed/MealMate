package com.iti.mealmate.ui.meallist.view;

import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.ActivityMealListBinding;
import com.iti.mealmate.ui.common.ErrorOverlayHelper;

public class MealListUiStateHandler {

    private final ActivityMealListBinding binding;

    public MealListUiStateHandler(ActivityMealListBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        hideContent();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
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
        binding.recyclerMealList.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showEmptyState() {
        stopAndHideLoading();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        binding.recyclerMealList.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.textEmptyState.setText(R.string.discover_empty_state);
    }

    public void hideEmptyState() {
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        binding.recyclerMealList.setVisibility(View.VISIBLE);
    }

    public void showError(String message, Runnable retryAction) {
        stopAndHideLoading();
        hideContent();
        ErrorOverlayHelper.showError(binding.errorOverlay.getRoot(), message, retryAction);
    }

    public void showNoInternetError(Runnable retryAction) {
        stopAndHideLoading();
        hideContent();
        ErrorOverlayHelper.showNetworkError(binding.errorOverlay.getRoot(), retryAction);
    }

    private void hideContent() {
        binding.recyclerMealList.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    private void stopAndHideLoading() {
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.stopShimmer();
        shimmer.setVisibility(View.GONE);
    }
}


