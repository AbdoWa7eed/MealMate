package com.iti.mealmate.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.databinding.FragmentHomeShimmerBinding;

public class HomeShimmerFragment extends Fragment {

    private FragmentHomeShimmerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeShimmerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            binding.shimmerContainer.startShimmer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding != null) {
            binding.shimmerContainer.stopShimmer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
