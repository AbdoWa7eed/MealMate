package com.iti.mealmate.ui.meallist.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.databinding.ItemMealListBinding;

import java.util.ArrayList;
import java.util.List;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder> {

    private final List<MealLight> meals = new ArrayList<>();

    public void submitList(List<MealLight> newMeals) {
        meals.clear();
        if (newMeals != null) {
            meals.addAll(newMeals);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealListBinding binding = ItemMealListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealLight meal = meals.get(position);

        holder.binding.textMealName.setText(meal.getName());

        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnailUrl())
                .into(holder.binding.imageMealThumbnail);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        final ItemMealListBinding binding;

        MealViewHolder(@NonNull ItemMealListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


