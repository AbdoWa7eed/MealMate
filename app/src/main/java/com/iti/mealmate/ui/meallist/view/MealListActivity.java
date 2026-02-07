package com.iti.mealmate.ui.meallist.view;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.databinding.ActivityMealListBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.common.MealListArgs;
import com.iti.mealmate.ui.common.RxTextView;
import com.iti.mealmate.ui.meallist.MealListPresenter;
import com.iti.mealmate.ui.meallist.MealListView;
import com.iti.mealmate.ui.meallist.presenter.MealListPresenterImpl;
import com.iti.mealmate.ui.meallist.view.adapter.MealListAdapter;
import com.iti.mealmate.ui.mealdetail.view.MealDetailsActivity;
import java.util.List;

public class MealListActivity extends AppCompatActivity implements MealListView {

    public static final String EXTRA_ARGS = "extra_meal_list_args";


    private ActivityMealListBinding binding;
    private MealListUiStateHandler uiStateHandler;
    private MealListAdapter mealListAdapter;
    private MealListPresenter presenter;
    private MealListArgs args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMealListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initObjects();
        initRecyclerView();
        setupToolbar();
        renderArgs(args);
        setupSearchListener();

        presenter.loadList(args);
    }

    private void initObjects() {
        uiStateHandler = new MealListUiStateHandler(binding);
        presenter = new MealListPresenterImpl(
                this,
                ServiceLocator.getMealRepository()
        );
        args = getIntent().getParcelableExtra(EXTRA_ARGS);
    }

    private void initRecyclerView() {
        mealListAdapter = new MealListAdapter();
        mealListAdapter.setOnMealClickListener(meal -> navigateToMealDetails(meal.getId()));
        binding.recyclerMealList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerMealList.setAdapter(mealListAdapter);
    }

    private void navigateToMealDetails(String mealId) {
        Bundle data = new Bundle();
        data.putString(MealDetailsActivity.EXTRA_MEAL_ID, mealId);
        ActivityExtensions.navigateToActivity(this, MealDetailsActivity.class, data);
    }
    private void setupToolbar() {
        ActivityExtensions.setStatusBarWithDarkIcons(this);

        ActivityExtensions.enableBackButtonWithTitle(
                this,
                binding.mealListToolbar,
                args.resolveTitle(this)
        );
    }

    private void renderArgs(MealListArgs args) {
        if (args == null) return;

        if (args.getFilterType() == FilterType.SEARCH) {
            binding.editTextSearch.setText(args.getQuery());
        }
    }


    private void setupSearchListener() {
        presenter.setupSearchObservable(RxTextView.textChanges(binding.editTextSearch));
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
    public void noInternetError() {
        uiStateHandler.showNoInternetError(presenter::retry);
    }

    @Override
    public void showMeals(List<MealLight> meals) {
        mealListAdapter.submitList(meals);
        uiStateHandler.showContent();
    }

    @Override
    public void showEmptyState() {
        uiStateHandler.showEmptyState();
    }

    @Override
    public void hideEmptyState() {
        uiStateHandler.hideEmptyState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }


}
