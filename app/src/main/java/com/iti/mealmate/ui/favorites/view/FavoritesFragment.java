package com.iti.mealmate.ui.favorites.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.R;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.FragmentFavoritesBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.common.DialogUtils;
import com.iti.mealmate.ui.favorites.FavoritePresenter;
import com.iti.mealmate.ui.favorites.FavoriteView;
import com.iti.mealmate.ui.favorites.presenter.FavoritePresenterImpl;
import com.iti.mealmate.ui.favorites.view.adapter.FavoriteAdapter;
import com.iti.mealmate.ui.mealdetail.view.MealDetailsActivity;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoriteView {

    private FragmentFavoritesBinding binding;
    private FavoritePresenter presenter;
    private FavoriteAdapter adapter;
    private FavoriteUiStateHandler uiStateHandler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiStateHandler = new FavoriteUiStateHandler(binding);
        setupRecyclerView();
        initPresenter();
    }

    private void setupRecyclerView() {
        adapter = new FavoriteAdapter(this::navigateToDetails, this::showDeleteConfirmation);
        binding.rvFavorites.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvFavorites.setAdapter(adapter);
    }

    private void initPresenter() {
        presenter = new FavoritePresenterImpl(this, ServiceLocator.getFavoriteRepository());
        presenter.onViewCreated();
    }

    private void showDeleteConfirmation(Meal meal) {
        DialogUtils.showConfirmationDialog(
                requireContext(),
                getString(R.string.remove_favorite_title),
                getString(R.string.remove_favorite_message),
                v -> presenter.removeFavorite(meal)
        );
    }

    @Override
    public void showFavorites(List<Meal> favorites) {
        uiStateHandler.showContent();
        adapter.setMeals(favorites);
    }

    @Override
    public void showEmptyState() {
        uiStateHandler.showEmptyState(getString(R.string.no_favorites_message));
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showPageError(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public void showErrorMessage(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public void noInternetError() {
        ActivityExtensions.showErrorSnackBar(requireActivity(), getString(R.string.error_network_subtitle));
    }

    @Override
    public void showSuccessMessage(String message) {
        ActivityExtensions.showSuccessSnackBar(requireActivity(), message);
    }

    public void navigateToDetails(Meal meal) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MealDetailsActivity.EXTRA_MEAL, meal);
        ActivityExtensions.navigateToActivity(requireActivity(), MealDetailsActivity.class, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
        binding = null;
    }
}
