package com.iti.mealmate.ui.meallist.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iti.mealmate.R;
import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.ActivityMealListBinding;
import com.iti.mealmate.ui.meallist.view.adapter.MealListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MealListActivity extends AppCompatActivity implements BaseView {

    private ActivityMealListBinding binding;
    private MealListUiStateHandler uiStateHandler;
    private MealListAdapter mealListAdapter;
    private final List<Meal> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMealListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uiStateHandler = new MealListUiStateHandler(binding);

        setupRecyclerView();
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
        uiStateHandler.showError(message, () -> {});

    }

    @Override
    public void noInternetError() {
        uiStateHandler.showNoInternetError(() -> {});
    }

    private void setupRecyclerView() {
        binding.recyclerMealList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        mealListAdapter = new MealListAdapter();
        mealListAdapter.submitList(meals);
        binding.recyclerMealList.setAdapter(mealListAdapter);
    }
}