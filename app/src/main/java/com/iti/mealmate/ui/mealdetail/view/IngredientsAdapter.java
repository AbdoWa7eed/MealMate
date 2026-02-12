package com.iti.mealmate.ui.mealdetail.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.ui.common.ImageLoader;
import com.iti.mealmate.data.meal.model.entity.MealIngredient;
import com.iti.mealmate.databinding.ItemIngredientBinding;

import java.util.Collections;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<MealIngredient> ingredients = Collections.emptyList();

    public void setIngredients(List<MealIngredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        MealIngredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        public IngredientViewHolder(@NonNull ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MealIngredient ingredient) {
            binding.tvIngredientName.setText(ingredient.getName());
            binding.tvIngredientMeasure.setText(ingredient.getMeasure());
            
            ImageLoader.loadWithMealPlaceHolder(itemView.getContext(), ingredient.getImageUrl(), binding.ivIngredientImage);
        }
    }
}
