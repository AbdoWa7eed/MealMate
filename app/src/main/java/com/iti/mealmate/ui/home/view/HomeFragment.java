package com.iti.mealmate.ui.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.home.HomePresenter;
import com.iti.mealmate.ui.home.HomeView;
import com.iti.mealmate.ui.home.presenter.HomePresenterImpl;
import com.iti.mealmate.ui.home.view.adapter.CategoryAdapter;
import com.iti.mealmate.ui.home.view.adapter.TrendingRecipeAdapter;
import com.iti.mealmate.ui.utils.FragmentStateManager;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private FragmentHomeBinding binding;
    private HomePresenter presenter;

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupHomeViews();

        if(presenter == null) {
            presenter = new HomePresenterImpl(this, ServiceLocator.getMealRepository());
        }
        presenter.loadHomeData();


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
            showContent();
        }
    }

    @Override
    public void showTrendingMeals(List<Meal> meals) {
        binding.recyclerTrending.setAdapter(new TrendingRecipeAdapter(meals));
        showContent();
    }

    @Override
    public void showLoading() {
        if (isAdded()) {
            hideContent();
            FragmentStateManager.showShimmerFragment(
                    getChildFragmentManager(),
                    R.id.state_fragment_container
            );
        }
    }

    @Override
    public void hideLoading() {
        if (isAdded()) {
            FragmentStateManager.hideShimmerFragment(getChildFragmentManager());
            showContent();
        }
    }

    @Override
    public void showError(String message) {
        if (isAdded()) {
            hideContent();
            String errorTitle = getString(R.string.error_title_default);
            String errorMessage = message != null && !message.isEmpty()
                    ? message
                    : getString(R.string.error_subtitle_default);

            FragmentStateManager.showErrorFragment(
                    getChildFragmentManager(),
                    R.id.state_fragment_container,
                    errorTitle,
                    errorMessage,
                    presenter::loadHomeData
            );
        }
    }

    private void showContent() {
        binding.contentContainer.setVisibility(View.VISIBLE);
    }

    private void hideContent() {
        binding.contentContainer.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }
}

