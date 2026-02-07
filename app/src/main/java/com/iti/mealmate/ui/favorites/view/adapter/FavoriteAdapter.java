package com.iti.mealmate.ui.favorites.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.databinding.ItemFavoriteMealBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private final Consumer<Meal> onMealClicked;
    private final Consumer<Meal> onFavoriteClicked;

    public FavoriteAdapter(Consumer<Meal> onMealClicked, Consumer<Meal> onFavoriteClicked) {
        this.onMealClicked = onMealClicked;
        this.onFavoriteClicked = onFavoriteClicked;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteMealBinding binding = ItemFavoriteMealBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.binding.tvFavoriteMealName.setText(meal.getName());
        
        holder.binding.tvFavoriteCategory.setText(meal.getCategory());
        holder.binding.tvFavoriteCountry.setText(meal.getArea());

        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnailUrl())
                .placeholder(R.color.colorSkeleton)
                .error(R.drawable.error_image)
                .into(holder.binding.imageFavoriteMeal);

        holder.binding.imageFavStatus.setImageResource(R.drawable.ic_fav_filled_primary);

        holder.binding.btnFavorite.setOnClickListener(v -> onFavoriteClicked.accept(meal));
        holder.itemView.setOnClickListener(v -> onMealClicked.accept(meal));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final ItemFavoriteMealBinding binding;

        public FavoriteViewHolder(@NonNull ItemFavoriteMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
