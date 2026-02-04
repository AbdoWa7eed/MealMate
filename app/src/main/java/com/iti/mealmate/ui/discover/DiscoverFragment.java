package com.iti.mealmate.ui.discover;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.iti.mealmate.R;
import com.iti.mealmate.data.filter.model.entity.FilterItem;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.databinding.FragmentDiscoverBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.common.RxTextView;
import com.iti.mealmate.ui.discover.adapter.DiscoverAdapter;
import com.iti.mealmate.ui.discover.adapter.GridSpacingItemDecoration;
import com.iti.mealmate.ui.discover.presenter.DiscoverPresenterImpl;

import java.util.List;


public class DiscoverFragment extends Fragment implements DiscoverView {

    private FragmentDiscoverBinding binding;
    private DiscoverPresenter presenter;
    private DiscoverAdapter discoverAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPresenter();
        setupViews();
        presenter.loadInitialFilters();
    }

    private void setupViews() {
        discoverAdapter = new DiscoverAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        binding.recyclerDiscoverResults.setLayoutManager(layoutManager);
        binding.recyclerDiscoverResults.setAdapter(discoverAdapter);
        int spacingInPixels = (int) (8 * getResources().getDisplayMetrics().density);
        binding.recyclerDiscoverResults.addItemDecoration(
                new GridSpacingItemDecoration(2, spacingInPixels, true));
        binding.chipCategory.setChecked(true);
        binding.chipCategory.setOnClickListener(
                v -> presenter.onFilterTypeSelected(FilterType.CATEGORY)
        );
        binding.chipCountry.setOnClickListener(
                v -> presenter.onFilterTypeSelected(FilterType.COUNTRY)
        );
        binding.chipIngredients.setOnClickListener(
                v -> presenter.onFilterTypeSelected(FilterType.INGREDIENT)
        );
        presenter.setupSearchObservable(RxTextView.textChanges(binding.editTextSearch));
    }
    private void setupPresenter() {
        if (presenter == null) {
            presenter = new DiscoverPresenterImpl(
                    this,
                    ServiceLocator.getFilterRepository()
            );
        }
    }


    @Override
    public void showLoading() {
        hideEmptyState();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    @Override
    public void hideLoading() {
        stopAndHideLoading();
        binding.recyclerDiscoverResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFilters(List<FilterItem> items) {
        stopAndHideLoading();
        hideEmptyState();
        discoverAdapter.setItems(items);
        binding.recyclerDiscoverResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyState() {
        stopAndHideLoading();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        binding.emptyStateContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyState() {
        binding.emptyStateContainer.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        stopAndHideLoading();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);
        String errorMessage = (message != null && !message.isEmpty())
                ? message
                : getString(R.string.error_subtitle_default);
        ActivityExtensions.showErrorSnackBar(
                requireActivity(),
                errorMessage
        );
    }

    @Override
    public void noInternetError() {
        stopAndHideLoading();
        binding.recyclerDiscoverResults.setVisibility(View.GONE);

        ActivityExtensions.showErrorSnackBar(
                requireActivity(),
                getString(R.string.error_network_subtitle)
        );
    }

    private void stopAndHideLoading() {
        ShimmerFrameLayout shimmer = binding.shimmerOverlay.shimmerContainer;
        shimmer.stopShimmer();
        shimmer.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
