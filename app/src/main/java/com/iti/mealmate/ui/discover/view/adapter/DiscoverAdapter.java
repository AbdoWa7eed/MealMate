package com.iti.mealmate.ui.discover.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.mealmate.databinding.ItemDiscoverResultBinding;
import com.iti.mealmate.data.filter.model.entity.FilterItem;

import java.util.ArrayList;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private List<FilterItem> items;

    public DiscoverAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<FilterItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
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
                Glide.with(holder.binding.getRoot().getContext())
                        .load(item.getImageUrl())
                        .into(holder.binding.imageDiscoverThumbnail);
            }
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

