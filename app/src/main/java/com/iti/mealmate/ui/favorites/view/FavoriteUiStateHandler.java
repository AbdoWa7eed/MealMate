package com.iti.mealmate.ui.favorites.view;

import android.view.View;
import com.iti.mealmate.databinding.FragmentFavoritesBinding;

public class FavoriteUiStateHandler {

    private final FragmentFavoritesBinding binding;

    public FavoriteUiStateHandler(FragmentFavoritesBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showContent() {
        binding.rvFavorites.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showEmptyState(String message) {
        binding.rvFavorites.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.textEmptyState.setText(message);
    }

    public void hideEmptyState() {
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
        binding.rvFavorites.setVisibility(View.VISIBLE);
    }
}
