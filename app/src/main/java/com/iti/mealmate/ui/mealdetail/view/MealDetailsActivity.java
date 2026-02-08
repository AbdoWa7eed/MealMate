package com.iti.mealmate.ui.mealdetail.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.databinding.ActivityMealDetailsBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.common.UiUtils;
import com.iti.mealmate.ui.mealdetail.MealDetailsPresenter;
import com.iti.mealmate.ui.mealdetail.MealDetailsView;
import com.iti.mealmate.ui.mealdetail.presenter.MealDetailsPresenterImpl;

import java.util.List;

public class MealDetailsActivity extends AppCompatActivity implements MealDetailsView {

    public static final String EXTRA_MEAL = "extra_meal";
    public static final String EXTRA_MEAL_ID = "extra_meal_id";

    private ActivityMealDetailsBinding binding;
    private IngredientsAdapter ingredientsAdapter;
    private PreparationAdapter preparationAdapter;
    private MealDetailsPresenter presenter;
    private MealDetailsUiStateHandler uiStateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMealDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActivityExtensions.setStatusBarTransparent(this);

        initViews();
        initPresenter();
        loadData();
    }

    private void initViews() {
        uiStateHandler = new MealDetailsUiStateHandler(binding);

        ingredientsAdapter = new IngredientsAdapter();
        GridLayoutManager ingredientsLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        binding.rvIngredients.setLayoutManager(ingredientsLayoutManager);
        binding.rvIngredients.setAdapter(ingredientsAdapter);

        preparationAdapter = new PreparationAdapter();
        binding.rvPreparation.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPreparation.setAdapter(preparationAdapter);

        binding.btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        binding.btnAddPlan
                .setOnClickListener(v -> UiUtils
                        .showDatePicker(this, presenter::addToPlan));
        binding.btnFavorite.setOnClickListener(v -> presenter.toggleFavorite());
    }

    private void initPresenter() {
        presenter = new MealDetailsPresenterImpl(
                this, 
                ServiceLocator.getMealRepository(),
                ServiceLocator.getFavoriteRepository(),
                ServiceLocator.getPlanRepository()
        );
    }

    private void loadData() {
        Intent intent = getIntent();
        Meal meal = intent != null ? (Meal) intent.getSerializableExtra(EXTRA_MEAL) : null;
        String mealId = intent != null ? intent.getStringExtra(EXTRA_MEAL_ID) : null;
        presenter.handleIntent(meal, mealId);
        binding.btnVideo.setOnClickListener(v -> presenter.onVideoClicked());
    }

    @Override
    public void showContent() {
        uiStateHandler.showContent();
    }

    @Override
    public void showMealName(String name) {
        binding.tvMealName.setText(name);
    }

    @Override
    public void showVideo(String url) {
         PreparationVideoFragment fragment = PreparationVideoFragment.newInstance(url);

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(
                new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                        if (f instanceof PreparationVideoFragment) {
                            hideVideoLoading();
                            fm.unregisterFragmentLifecycleCallbacks(this);
                        }
                    }
                }, false);

        ActivityExtensions.navigateToFragment(this, R.id.video_container, fragment, PreparationVideoFragment.TAG);
    }

    @Override
    public void showVideoLoading() {
        binding.btnVideo.setVisibility(View.INVISIBLE);
        binding.progressVideo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVideoLoading() {
        binding.progressVideo.setVisibility(View.GONE);
        binding.btnVideo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMealImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.color.colorSkeleton)
                .error(R.drawable.error_image)
                .into(binding.ivMealImage);
    }

    @Override
    public void showCountry(String country) {
        binding.tvCountryName.setText(country);
    }

    @Override
    public void showIngredients(List<MealIngredient> ingredients) {
        ingredientsAdapter.setIngredients(ingredients);
    }

    @Override
    public void showPreparationSteps(List<String> steps) {
        preparationAdapter.setSteps(steps);
    }

    @Override
    public void showItemsCount(int count) {
        binding.tvItemsCount.setText(String.format(getString(R.string.meal_details_items_count), count));
    }

    @Override
    public void showFavoriteStatus(boolean isFavorite) {
        binding.btnFavorite.setImageResource(
                isFavorite ? R.drawable.ic_fav_filled_primary : R.drawable.ic_fav_filled
        );
    }

    @Override
    public void showSuccessMessage(String message) {
        ActivityExtensions.showSuccessSnackBar(this, message);
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
    public void showPageError(String message) {
        uiStateHandler.showError(message, presenter::retry);
    }

    @Override
    public void showErrorMessage(String message) {
        ActivityExtensions.showErrorSnackBar(this, message);
    }

    @Override
    public void noInternetError() {
        uiStateHandler.showNoInternetError(presenter::retry);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
