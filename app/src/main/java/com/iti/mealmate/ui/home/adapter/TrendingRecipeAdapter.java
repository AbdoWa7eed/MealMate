package com.iti.mealmate.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.ItemTrendingRecipeBinding;

import java.util.List;

public class TrendingRecipeAdapter extends RecyclerView.Adapter<TrendingRecipeAdapter.TrendingRecipeViewHolder> {

    private final List<TrendingRecipeItem> recipes;

    public TrendingRecipeAdapter(List<TrendingRecipeItem> recipes) {
        this.recipes = recipes;
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
        TrendingRecipeItem recipe = recipes.get(position);
        holder.binding.textTrendingName.setText(recipe.getName());
        holder.binding.textTrendingCategory.setText(recipe.getCategory());
        holder.binding.textTrendingCountry.setText(recipe.getCountry());
        holder.binding.imageTrendingThumbnail.setImageResource(recipe.getImageResId());
        
        holder.binding.buttonFavImage.setImageResource(
                recipe.isFavorite() ? R.drawable.ic_fav_filled_primary : R.drawable.ic_fav_filled);
        
        holder.binding.buttonTrendingFavorite.setOnClickListener(v -> {
            holder.binding.buttonFavImage.setImageResource(
                    recipe.isFavorite() ? R.drawable.ic_fav_filled : R.drawable.ic_fav_filled_primary);
            recipe.setFavorite(!recipe.isFavorite());
        });
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public static class TrendingRecipeViewHolder extends RecyclerView.ViewHolder {
        ItemTrendingRecipeBinding binding;

        public TrendingRecipeViewHolder(@NonNull ItemTrendingRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class TrendingRecipeItem {
        private final String name;
        private final String category;
        private final String country;
        private final int imageResId;
        private boolean isFavorite;

        public TrendingRecipeItem(String name, String category, String country, int imageResId) {
            this.name = name;
            this.category = category;
            this.country = country;
            this.imageResId = imageResId;
            this.isFavorite = false;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getCountry() {
            return country;
        }

        public int getImageResId() {
            return imageResId;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public void setFavorite(boolean favorite) {
            isFavorite = favorite;
        }
    }
}

