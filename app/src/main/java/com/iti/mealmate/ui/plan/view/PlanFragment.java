package com.iti.mealmate.ui.plan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iti.mealmate.R;
import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.databinding.FragmentPlanBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.common.DialogUtils;
import com.iti.mealmate.ui.mealdetail.view.MealDetailsActivity;
import com.iti.mealmate.ui.plan.PlanPresenter;
import com.iti.mealmate.ui.plan.PlanView;
import com.iti.mealmate.ui.plan.presenter.PlanPresenterImpl;
import com.iti.mealmate.ui.plan.view.adapter.PlansAdapter;

import java.util.List;

public class PlanFragment extends Fragment implements PlanView {

    private FragmentPlanBinding binding;
    private PlanPresenter presenter;
    private PlansAdapter plansAdapter;

    private PlanUiStateHandler planUiStateHandler;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPlanBinding.inflate(inflater, container, false);
        presenter = new PlanPresenterImpl(this, ServiceLocator.getPlanRepository(), ServiceLocator.getPreferencesHelper());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        planUiStateHandler = new PlanUiStateHandler(binding);
        setupRecyclerView();
        setupWeekSelector();
        presenter.onViewCreated();

    }

    private void setupRecyclerView() {
        plansAdapter = new PlansAdapter(this::showConfirmationDialog, this::navigateToMealDetails);
        binding.recyclerPlan.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerPlan.setAdapter(plansAdapter);
        binding.recyclerPlan.setHasFixedSize(false);

    }

    private void showConfirmationDialog(PlannedMeal meal) {
        DialogUtils.showConfirmationDialog(
                requireContext(),
                getString(R.string.remove_plan_title),
                getString(R.string.remove_plan_message),
                v -> presenter.removeMeal(meal)
        );
    }

    private void navigateToMealDetails(PlannedMeal meal) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MealDetailsActivity.EXTRA_MEAL, meal.getMeal());
        ActivityExtensions.navigateToActivity(requireActivity(), MealDetailsActivity.class, bundle);
    }

    private void setupWeekSelector() {
        TextView thisWeek = binding.btnThisWeek;
        TextView nextWeek = binding.btnNextWeek;
        planUiStateHandler.initializeWeekSelector();
        thisWeek.setOnClickListener(v -> {
            planUiStateHandler.toggleWeek(thisWeek, nextWeek);
            presenter.loadCurrentWeek();
        });
        nextWeek.setOnClickListener(v -> {
            planUiStateHandler.toggleWeek(nextWeek, thisWeek);
            presenter.loadNextWeek();
        });
        binding.textDateRange.setText(DateUtils.getTwoWeekDateRange());
    }

    @Override
    public void showPlannedMeals(List<DayPlan> plannedMealList) {
        planUiStateHandler.showContent();
        plansAdapter.submitList(plannedMealList);
    }

    @Override
    public void showEmptyState() {
        planUiStateHandler.showEmptyState(getString(R.string.no_plans_message));
    }


    @Override
    public void showLoading() {
        planUiStateHandler.showLoading();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showPageError(String message) {
        planUiStateHandler.showErrorPage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public void showSuccessMessage(String message) {
        ActivityExtensions.showSuccessSnackBar(requireActivity(), message);
    }

    @Override
    public void noInternetError() {
        PlanView.super.noInternetError();
    }

    @Override
    public void showGuestMode() {
        planUiStateHandler.showGuestMode(requireActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
