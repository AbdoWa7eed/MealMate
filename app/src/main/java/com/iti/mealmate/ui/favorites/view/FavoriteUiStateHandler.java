package com.iti.mealmate.ui.favorites.view;

import android.app.Activity;
import android.view.View;
import com.iti.mealmate.databinding.FragmentFavoritesBinding;
import com.iti.mealmate.ui.common.GuestOverlayHelper;

public class FavoriteUiStateHandler {

    private final FragmentFavoritesBinding binding;

    public FavoriteUiStateHandler(FragmentFavoritesBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        hideGuestMode();
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.GONE);
        binding.shimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.shimmerLayout.shimmerContainer.startShimmer();
    }

    public void showContent() {
        hideGuestMode();
        binding.shimmerLayout.shimmerContainer.stopShimmer();
        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showEmptyState(String message) {
        hideGuestMode();
        binding.shimmerLayout.shimmerContainer.stopShimmer();
        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.textEmptyState.setText(message);
    }

    public void showGuestMode(Activity activity) {
        binding.shimmerLayout.shimmerContainer.stopShimmer();
        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        GuestOverlayHelper.showGuestOverlay(binding.getRoot(), activity);
    }

    public void showErrorPage(String message) {
        hideGuestMode();
        binding.shimmerLayout.shimmerContainer.stopShimmer();
        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.VISIBLE);
        binding.errorOverlay.errorSubtitle.setText(message);
    }

    public void hideGuestMode() {
        GuestOverlayHelper.hideGuestOverlay(binding.guestOverlay.getRoot());
    }

    public void hideEmptyState() {
        binding.shimmerLayout.shimmerContainer.stopShimmer();
        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
        binding.errorOverlay.getRoot().setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.VISIBLE);
    }
}
