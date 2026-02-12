package com.iti.mealmate.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentErrorBinding;

public class ErrorFragment extends Fragment {

    private FragmentErrorBinding binding;
    private static final String ARG_TITLE = "title";
    private static final String ARG_SUBTITLE = "subtitle";
    private static final String ARG_SHOW_RETRY = "show_retry";
    private static final String ARG_IS_CONNECTION_ERROR = "is_connection_error";

    private Runnable retryAction;

    public static ErrorFragment newInstance(String title, boolean showRetry) {
        ErrorFragment fragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putBoolean(ARG_SHOW_RETRY, showRetry);
        args.putBoolean(ARG_IS_CONNECTION_ERROR, false);
        fragment.setArguments(args);
        return fragment;
    }

    public static ErrorFragment newConnectionErrorInstance(boolean showRetry) {
        ErrorFragment fragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_RETRY, showRetry);
        args.putBoolean(ARG_IS_CONNECTION_ERROR, true);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRetryAction(Runnable retryAction) {
        this.retryAction = retryAction;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentErrorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayErrorData();
        setupRetryButton();
    }

    private void displayErrorData() {
        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE, "");
            boolean showRetry = getArguments().getBoolean(ARG_SHOW_RETRY, false);
            boolean isConnectionError = getArguments().getBoolean(ARG_IS_CONNECTION_ERROR, false);

            if (isConnectionError) {
                binding.errorTitle.setText(R.string.error_network_title);
                binding.errorSubtitle.setText(R.string.error_network_subtitle);
                binding.errorImage.setImageResource(R.drawable.no_connection_image);
            } else {
                binding.errorTitle.setText(title);
            }
            binding.retryButton.setVisibility(showRetry ? View.VISIBLE : View.GONE);
        }
    }

    private void setupRetryButton() {
        binding.retryButton.setOnClickListener(v -> {
            if (retryAction != null) {
                retryAction.run();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        retryAction = null;
    }
}
