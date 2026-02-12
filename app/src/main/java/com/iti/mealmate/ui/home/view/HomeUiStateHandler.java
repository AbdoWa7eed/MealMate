package com.iti.mealmate.ui.home.view;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.ui.common.ErrorOverlayHelper;

public class HomeUiStateHandler {

    private final FragmentHomeBinding binding;

    public HomeUiStateHandler(FragmentHomeBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        hideContent();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.setVisibility(android.view.View.VISIBLE);
        shimmer.startShimmer();
    }

    public void hideLoading() {
        stopAndHideLoading();
    }

    public void showContent() {
        stopAndHideLoading();
        ErrorOverlayHelper.hideError(binding.errorOverlay.getRoot());
        binding.contentContainer.setVisibility(android.view.View.VISIBLE);
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
        binding.contentContainer.setVisibility(android.view.View.GONE);
    }

    private void stopAndHideLoading() {
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.stopShimmer();
        shimmer.setVisibility(android.view.View.GONE);
    }
}


