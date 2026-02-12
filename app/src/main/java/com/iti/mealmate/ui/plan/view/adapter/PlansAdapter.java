package com.iti.mealmate.ui.plan.view.adapter;


import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.databinding.ItemPlanDayBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlansAdapter
        extends RecyclerView.Adapter<PlansAdapter.PlanViewHolder> {

    private final List<DayPlan> plans = new ArrayList<>();
    private final SparseBooleanArray expandedStates = new SparseBooleanArray();
    private final Consumer<PlannedMeal> onMealRemoveListener;
    private final Consumer<PlannedMeal> onMealSelected;



    public  PlansAdapter(Consumer<PlannedMeal> onMealRemoveListener,Consumer<PlannedMeal> onMealSelected ) {
        this.onMealRemoveListener = onMealRemoveListener;
        this.onMealSelected = onMealSelected;
        expandedStates.clear();
    }

    public void submitList(List<DayPlan> newPlans) {
        plans.clear();

        if (newPlans != null) {
            plans.addAll(newPlans);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        ItemPlanDayBinding binding =
                ItemPlanDayBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );

        return new PlanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {

        DayPlan plan = plans.get(position);

        boolean isExpanded = expandedStates.get(position, false);

        holder.binding.textDayName.setText(plan.getLocalDate().getDayOfWeek().name());

        holder.binding.textTodayBadge.setVisibility(
                plan.isToday() ? View.VISIBLE : View.GONE
        );

        holder.binding.recyclerDayMeals.setVisibility(
                isExpanded ? View.VISIBLE : View.GONE
        );

        holder.binding.imageExpand.setRotation(isExpanded ? 180f : 0f);

        holder.binding.imageExpand.setOnClickListener(v -> {
            boolean newState = !expandedStates.get(position, false);
            expandedStates.put(position, newState);
            notifyItemChanged(position);
        });

        PlannedMealsAdapter mealsAdapter = new PlannedMealsAdapter();
        mealsAdapter.submitList(plan.getMeals());
        holder.binding.recyclerDayMeals.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.binding.recyclerDayMeals.setAdapter(mealsAdapter);
        mealsAdapter.setOnMealRemoveListener(onMealRemoveListener);
        mealsAdapter.setOnMealSelected(onMealSelected);


    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        final ItemPlanDayBinding binding;

        PlanViewHolder(@NonNull ItemPlanDayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}