package com.iti.mealmate.ui.discover.view;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.iti.mealmate.data.filter.model.entity.FilterItem;
import com.iti.mealmate.data.filter.model.entity.FilterType;
import com.iti.mealmate.databinding.FragmentDiscoverBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.common.RxTextView;
import com.iti.mealmate.ui.common.UiUtils;
import com.iti.mealmate.ui.discover.DiscoverPresenter;
import com.iti.mealmate.ui.discover.DiscoverView;
import com.iti.mealmate.ui.discover.presenter.DiscoverPresenterImpl;
import com.iti.mealmate.ui.discover.view.adapter.DiscoverAdapter;
import com.iti.mealmate.ui.discover.view.adapter.GridSpacingItemDecoration;

import java.util.List;


public class DiscoverFragment extends Fragment implements DiscoverView {

    private FragmentDiscoverBinding binding;
    private DiscoverPresenter presenter;
    private DiscoverAdapter discoverAdapter;
    private DiscoverUiStateHandler uiStateHandler;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        uiStateHandler = new DiscoverUiStateHandler(binding);
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
        setupRecyclerView();
        setupFilterChips();
        setupSearch();
    }

    private void setupRecyclerView() {
        discoverAdapter = new DiscoverAdapter();
        binding.recyclerDiscoverResults
                .setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerDiscoverResults.setAdapter(discoverAdapter);
        var pixels = UiUtils.dpToPx(requireContext(), 8);
        binding.recyclerDiscoverResults
                .addItemDecoration(new GridSpacingItemDecoration(2, pixels, true));
    }

    private void setupFilterChips() {
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
    }

    private void setupSearch() {
        presenter.setupSearchObservable(
                RxTextView.textChanges(binding.editTextSearch)
        );
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
        uiStateHandler.showLoading();
    }

    @Override
    public void hideLoading() {
        uiStateHandler.hideLoading();
    }

    @Override
    public void showFilters(List<FilterItem> items) {
        discoverAdapter.setItems(items);
        uiStateHandler.showFiltersState();
    }

    @Override
    public void showEmptyState() {
        uiStateHandler.showEmptyState();
    }

    @Override
    public void hideEmptyState() {
        uiStateHandler.hideEmptyState();
    }

    @Override
    public void showError(String message) {
        uiStateHandler.showError(message, presenter::loadInitialFilters);
    }

    @Override
    public void noInternetError() {
        uiStateHandler.showNoInternetError(presenter::loadInitialFilters);
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
