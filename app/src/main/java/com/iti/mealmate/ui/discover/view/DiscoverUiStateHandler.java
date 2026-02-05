package com.iti.mealmate.ui.discover.view;

import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.mealmate.databinding.FragmentDiscoverBinding;
import com.iti.mealmate.ui.common.ErrorOverlayHelper;

public class DiscoverUiStateHandler {

    private final FragmentDiscoverBinding binding;

    public DiscoverUiStateHandler(FragmentDiscoverBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        hideEmptyState();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    public void hideLoading() {
        stopAndHideLoading();
        binding.recyclerDiscoverResults.setVisibility(View.VISIBLE);
    }

    public void showFiltersState() {
        stopAndHideLoading();
        hideEmptyState();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        binding.recyclerDiscoverResults.setVisibility(View.VISIBLE);
    }

    public void showEmptyState() {
        stopAndHideLoading();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        binding.emptyStateContainer.setVisibility(View.VISIBLE);
    }

    public void hideEmptyState() {
        binding.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showError(String message, Runnable retryAction) {
        stopAndHideLoading();
        hideEmptyState();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        ErrorOverlayHelper.showError(binding.errorOverlay.getRoot(), message, retryAction);
    }

    public void showNoInternetError(Runnable retryAction) {
        stopAndHideLoading();
        hideEmptyState();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        ErrorOverlayHelper.showNetworkError(binding.errorOverlay.getRoot(), retryAction);
    }

    private void stopAndHideLoading() {
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.stopShimmer();
        shimmer.setVisibility(View.GONE);
    }
}


