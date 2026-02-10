package com.iti.mealmate.ui.plan.presenter;

import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.repo.plan.PlanRepository;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.ui.plan.PlanPresenter;
import com.iti.mealmate.ui.plan.PlanView;
import com.iti.mealmate.ui.plan.WeekPlanStatus;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {

    private final PlanRepository planRepository;

    private List<DayPlan> plannedList;

    private final CompositeDisposable disposables;

    private final PlanView view;

    private final PreferencesHelper preferencesHelper;
    private WeekPlanStatus status = WeekPlanStatus.CURRENT_WEEK;

    public PlanPresenterImpl(PlanView view, PlanRepository planRepository,
                             PreferencesHelper preferencesHelper) {
        this.planRepository = planRepository;
        this.view = view;
        this.preferencesHelper = preferencesHelper;
        disposables = new CompositeDisposable();
    }


    @Override
    public void onViewCreated() {
        String uid = preferencesHelper.getUserId();
        if(uid == null){
            // TODO: HANDLE GUEST USER
            view.showPageError("User Not Found");
            return;
        }
        var request = planRepository
                .getPlannedMealsForNextTwoWeeks(preferencesHelper.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadData,
                        throwable -> view.showPageError(throwable.getMessage()));
        disposables.add(request);
    }

    private void loadData(List<DayPlan> plannedList) {
        if (plannedList == null || plannedList.isEmpty()) {
            view.showEmptyState();
            return;
        }
        this.plannedList = plannedList;
        if (status == WeekPlanStatus.CURRENT_WEEK) {
            loadCurrentWeek();
        } else {
            loadNextWeek();
        }
    }

    @Override
    public void loadCurrentWeek() {
        if(plannedList == null)
            return;
        var currentWeakList = plannedList
                .stream()
                .filter(dayPlan -> DateUtils.isCurrentWeek(dayPlan.getDate()))
                .collect(Collectors.toList());
        status = WeekPlanStatus.CURRENT_WEEK;
        if(currentWeakList.isEmpty()){
            view.showEmptyState();
            return;
        }
        view.showPlannedMeals(currentWeakList);
    }

    @Override
    public void loadNextWeek() {
        if(plannedList == null)
            return;
        var nextWeakList = plannedList
                .stream()
                .filter(dayPlan -> !DateUtils.isCurrentWeek(dayPlan.getDate()))
                .collect(Collectors.toList());
        status = WeekPlanStatus.NEXT_WEEK;
        if(nextWeakList.isEmpty()){
            view.showEmptyState();
            return;
        }
        view.showPlannedMeals(nextWeakList);
    }

    @Override
    public void removeMeal(PlannedMeal meal) {
        var request = planRepository
                .removePlannedMealFromDay(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.showSuccessMessage("Item Removed Success Fully"),
                        throwable -> view.showErrorMessage(throwable.getMessage())
                );
        disposables.add(request);
    }



    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
