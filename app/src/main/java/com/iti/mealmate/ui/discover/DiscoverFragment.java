package com.iti.mealmate.ui.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.databinding.FragmentDiscoverBinding;

public class DiscoverFragment extends Fragment {

    private FragmentDiscoverBinding binding;

    private BaseAdapter discoverAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSearchBar();
        setupChips();
        setupGrid();
    }

    private void setupSearchBar() {
    }

    private void setupChips() {
    }

    private void setupGrid() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        discoverAdapter = null;
    }
}
