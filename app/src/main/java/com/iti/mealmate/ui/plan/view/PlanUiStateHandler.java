package com.iti.mealmate.ui.plan.view;

import android.graphics.Typeface;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentPlanBinding;

public class PlanUiStateHandler {

    private final FragmentPlanBinding binding;
    private View indicator;


    public PlanUiStateHandler(FragmentPlanBinding binding) {
        this.binding = binding;
    }

    public void showLoading() {
        binding.recyclerPlan.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showContent() {
        binding.recyclerPlan.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.GONE);
    }

    public void showEmptyState(String message) {
        binding.recyclerPlan.setVisibility(View.GONE);
        binding.emptyStateLayout.emptyStateContainer.setVisibility(View.VISIBLE);
        binding.emptyStateLayout.textEmptyState.setText(message);
    }

    public void initializeWeekSelector() {
        indicator = binding.weekSelectorIndicator;
        TextView thisWeek = binding.btnThisWeek;
        thisWeek.post(() -> {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    thisWeek.getWidth(),
                    thisWeek.getHeight()
            );
            indicator.setLayoutParams(params);
            indicator.setTranslationX(thisWeek.getX());
        });
    }
    public void toggleWeek(TextView selected, TextView unselected) {
        indicator.animate()
                .translationX(selected.getX())
                .setDuration(250)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        selected.animate()
                .alpha(0.5f)
                .setDuration(125)
                .withEndAction(() -> {
                    selected.setTextColor(ContextCompat.getColor(selected.getContext(), R.color.colorTextPrimary));
                    selected.setTypeface(null, Typeface.BOLD);
                    selected.animate().alpha(1f).setDuration(125).start();
                })
                .start();

        unselected.animate()
                .alpha(0.5f)
                .setDuration(125)
                .withEndAction(() -> {
                    unselected.setTextColor(ContextCompat.getColor(unselected.getContext(), R.color.colorTextSecondary));
                    unselected.setTypeface(null, Typeface.NORMAL);
                    unselected.animate().alpha(1f).setDuration(125).start();
                })
                .start();
    }

}
