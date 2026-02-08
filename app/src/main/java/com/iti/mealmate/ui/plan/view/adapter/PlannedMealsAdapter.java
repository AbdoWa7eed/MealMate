package com.iti.mealmate.ui.plan.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.databinding.ItemPlanMealBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlannedMealsAdapter
        extends RecyclerView.Adapter<PlannedMealsAdapter.PlannedMealViewHolder> {


    private final List<PlannedMeal> meals = new ArrayList<>();
    private Consumer<PlannedMeal> removeListener;

    private Consumer<PlannedMeal> onMealSelected;


    public void setOnMealRemoveListener(Consumer<PlannedMeal> listener) {
        this.removeListener = listener;
    }

    public void setOnMealSelected(Consumer<PlannedMeal> listener) {
        this.onMealSelected = listener;
    }

    public void submitList(List<PlannedMeal> newMeals) {
        meals.clear();
        if (newMeals != null) {
            meals.addAll(newMeals);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlannedMealViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        ItemPlanMealBinding binding =
                ItemPlanMealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PlannedMealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannedMealViewHolder holder, int position) {
        PlannedMeal plannedMeal = meals.get(position);
        Meal meal = meals.get(position).getMeal();
        holder.binding.textMealName.setText(meal.getName());
        holder.binding.textMealCategory.setText(meal.getCategory());
        holder.binding.textMealCountry.setText(meal.getArea());
        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnailUrl())
                .into(holder.binding.imageMealThumbnail);

        holder.binding.buttonRemoveMeal.setOnClickListener(v -> {
            if (removeListener != null) {
                removeListener.accept(plannedMeal);
            }
        });
        holder.binding.getRoot().setOnClickListener(v -> {
                    if (onMealSelected != null) {
                        onMealSelected.accept(plannedMeal);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class PlannedMealViewHolder extends RecyclerView.ViewHolder {

        final ItemPlanMealBinding binding;

        PlannedMealViewHolder(@NonNull ItemPlanMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
