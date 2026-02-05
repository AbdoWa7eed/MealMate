package com.iti.mealmate.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.home.HomePresenter;
import com.iti.mealmate.ui.home.HomeView;
import com.iti.mealmate.ui.home.presenter.HomePresenterImpl;
import com.iti.mealmate.ui.home.view.adapter.CategoryAdapter;
import com.iti.mealmate.ui.home.view.adapter.TrendingRecipeAdapter;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private HomeUiStateHandler uiStateHandler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiStateHandler = new HomeUiStateHandler(binding);
        setupHomeViews();
        if (presenter == null) {
            presenter = new HomePresenterImpl(this, ServiceLocator.getMealRepository());
            presenter.loadHomeData();
        }
    }

    private void setupHomeViews() {
        binding.recyclerCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerCategories.setAdapter(new CategoryAdapter());
        binding.recyclerTrending.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        if (meal != null) {
            Glide.with(requireContext())
                    .load(meal.getThumbnailUrl())
                    .into(binding.imageMealOfDay);
            binding.textMealOfDayName.setText(meal.getName());
            binding.textMealOfDayCountry.setText(meal.getArea());
            uiStateHandler.showContent();
        }
    }

    @Override
    public void showTrendingMeals(List<Meal> meals) {
        binding.recyclerTrending.setAdapter(new TrendingRecipeAdapter(meals));
        uiStateHandler.showContent();
    }

    @Override
    public void showLoading() {
        uiStateHandler.showLoading();
    }

    @Override
    public void hideLoading() {
        uiStateHandler.hideLoading();
    }

    @Override
    public void showError(String message) {
        uiStateHandler.showError(message, presenter::loadHomeData);
    }

    @Override
    public void noInternetError() {
        uiStateHandler.showNoInternetError(presenter::loadHomeData);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}