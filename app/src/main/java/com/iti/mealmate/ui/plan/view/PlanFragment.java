package com.iti.mealmate.ui.plan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.databinding.FragmentPlanBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.plan.PlanPresenter;
import com.iti.mealmate.ui.plan.PlanView;
import com.iti.mealmate.ui.plan.presenter.PlanPresenterImpl;

import java.util.List;

public class PlanFragment extends Fragment implements PlanView {

    private FragmentPlanBinding binding;
    private PlanPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(getLayoutInflater());
        presenter = new PlanPresenterImpl(this, ServiceLocator.getPlanRepository());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();
    }

    @Override
    public void showPlannedMeals(List<DayPlan> plannedMealList) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showPageError(String message) {
        PlanView.super.showPageError(message);
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
}

