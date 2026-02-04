package com.iti.mealmate.ui.discover;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.filter.model.entity.FilterItem;

import java.util.List;

public interface DiscoverView extends BaseView {
    void showFilters(List<FilterItem> items);
    void showEmptyState();
    void hideEmptyState();
}

