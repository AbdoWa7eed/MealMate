package com.iti.mealmate.ui.discover.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.databinding.ItemDiscoverResultBinding;
import com.iti.mealmate.ui.common.ImageLoader;
import com.iti.mealmate.data.filter.model.entity.FilterItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private List<FilterItem> items;

    public DiscoverAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<FilterItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }


    private Consumer<FilterItem> onItemClicked;
    public void setOnItemClicked(Consumer<FilterItem> onItemClicked){
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDiscoverResultBinding binding = ItemDiscoverResultBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DiscoverViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        FilterItem item = items.get(position);
        if (item != null) {
            holder.binding.textDiscoverTitle.setText(item.getName());

            if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                ImageLoader.loadWithMealPlaceHolder(holder.binding.getRoot().getContext(), item.getImageUrl(), holder.binding.imageDiscoverThumbnail);
            }

            holder.binding.getRoot().setOnClickListener(v -> {
                if (onItemClicked != null) {
                    onItemClicked.accept(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        ItemDiscoverResultBinding binding;

        DiscoverViewHolder(@NonNull ItemDiscoverResultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

