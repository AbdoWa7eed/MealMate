package com.iti.mealmate.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.databinding.HomeCategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final List<CategoryItem> categories;

    public CategoryAdapter(List<CategoryItem> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeCategoryItemBinding binding = HomeCategoryItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem category = categories.get(position);
        holder.binding.textCategoryName.setText(category.getName());
        holder.binding.imageCategoryIcon.setImageResource(category.getIconResId());
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        HomeCategoryItemBinding binding;

        public CategoryViewHolder(@NonNull HomeCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class CategoryItem {
        private final String name;
        private final int iconResId;

        public CategoryItem(String name, int iconResId) {
            this.name = name;
            this.iconResId = iconResId;
        }

        public String getName() {
            return name;
        }

        public int getIconResId() {
            return iconResId;
        }
    }
}

