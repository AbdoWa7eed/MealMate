package com.iti.mealmate.ui.plan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentPlanBinding;

public class PlanFragment extends Fragment {

    private FragmentPlanBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("ONDISTORYPLAN", "onDestroy: ");
    }
}

