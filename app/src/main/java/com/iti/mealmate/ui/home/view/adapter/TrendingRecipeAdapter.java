package com.iti.mealmate.ui.home.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.ItemTrendingRecipeBinding;

import java.util.List;
import java.util.function.Consumer;

public class TrendingRecipeAdapter extends RecyclerView.Adapter<TrendingRecipeAdapter.TrendingRecipeViewHolder> {

    private final List<Meal> meals;
    private final Consumer<Meal> onMealClicked;
    private final Consumer<Meal> onFavoriteClicked;

    public TrendingRecipeAdapter(List<Meal> meals, Consumer<Meal> onMealClicked, Consumer<Meal> onFavoriteClicked) {
        this.meals = meals;
        this.onMealClicked = onMealClicked;
        this.onFavoriteClicked = onFavoriteClicked;
    }

    @NonNull
    @Override
    public TrendingRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingRecipeBinding binding = ItemTrendingRecipeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TrendingRecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingRecipeViewHolder holder, int position) {
        Meal recipe = meals.get(position);

        holder.binding.textTrendingName.setText(recipe.getName());
        holder.binding.textTrendingCategory.setText(recipe.getCategory());
        holder.binding.textTrendingCountry.setText(recipe.getArea());

        Glide.with(holder.itemView.getContext())
                .load(recipe.getThumbnailUrl())
                .into(holder.binding.imageTrendingThumbnail);

        holder.binding.buttonFavImage.setImageResource(
                recipe.isFavorite() ? R.drawable.ic_fav_filled_primary : R.drawable.ic_fav_filled
        );

        holder.binding.buttonTrendingFavorite.setOnClickListener(v -> onFavoriteClicked.accept(recipe));

        holder.itemView.setOnClickListener(v -> onMealClicked.accept(recipe));
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public static class TrendingRecipeViewHolder extends RecyclerView.ViewHolder {
        ItemTrendingRecipeBinding binding;

        public TrendingRecipeViewHolder(@NonNull ItemTrendingRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


