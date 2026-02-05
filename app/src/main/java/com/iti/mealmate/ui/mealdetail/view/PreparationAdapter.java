package com.iti.mealmate.ui.mealdetail.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mealmate.databinding.ItemPreparationStepBinding;

import java.util.Collections;
import java.util.List;

public class PreparationAdapter extends RecyclerView.Adapter<PreparationAdapter.StepViewHolder> {

    private List<String> steps = Collections.emptyList();

    public void setSteps(List<String> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPreparationStepBinding binding = ItemPreparationStepBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        private final ItemPreparationStepBinding binding;

        public StepViewHolder(@NonNull ItemPreparationStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String step, int number) {
            binding.tvStepNumber.setText(String.valueOf(number));
            binding.tvStepDescription.setText(step);
        }
    }
}
