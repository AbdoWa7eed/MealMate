package com.iti.mealmate.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentHomeBinding;
import com.iti.mealmate.ui.home.adapter.CategoryAdapter;
import com.iti.mealmate.ui.home.adapter.TrendingRecipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setupCategoriesRecyclerView();
        setupTrendingRecyclerView();
    }

    private void setupCategoriesRecyclerView() {
        List<CategoryAdapter.CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryAdapter.CategoryItem("Breakfast", R.drawable.ic_breakfast));
        categories.add(new CategoryAdapter.CategoryItem("Seafood", R.drawable.ic_seafood));
        categories.add(new CategoryAdapter.CategoryItem("Vegan", R.drawable.ic_vegan));
        categories.add(new CategoryAdapter.CategoryItem("Dessert", R.drawable.ic_desert));
        binding.recyclerCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerCategories.setAdapter(new CategoryAdapter(categories));
    }

    private void setupTrendingRecyclerView() {
        List<TrendingRecipeAdapter.TrendingRecipeItem> recipes = new ArrayList<>();
        recipes.add(new TrendingRecipeAdapter.TrendingRecipeItem(
                "Spicy Tacos", "Chicken", "Mexico", R.drawable.onboarding_image_1));
        recipes.add(new TrendingRecipeAdapter.TrendingRecipeItem(
                "Avocado Salad Bowl", "Vegetarian", "China", R.drawable.onboarding_image_2));
        recipes.add(new TrendingRecipeAdapter.TrendingRecipeItem(
                "Pasta Carbonara", "Pasta", "Italy", R.drawable.onboarding_image_3));
        recipes.add(new TrendingRecipeAdapter.TrendingRecipeItem(
                "Sushi Platter", "Japanese", "Japan", R.drawable.onboarding_image_1));

        binding.recyclerTrending.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerTrending.setAdapter(new TrendingRecipeAdapter(recipes));
    }
}

