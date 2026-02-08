package com.iti.mealmate.ui.plan.presenter;

import com.iti.mealmate.data.meal.model.entity.DayPlan;
import com.iti.mealmate.data.meal.model.entity.PlannedMeal;
import com.iti.mealmate.data.meal.repo.plan.PlanRepository;
import com.iti.mealmate.ui.plan.PlanPresenter;
import com.iti.mealmate.ui.plan.PlanView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {

    private final PlanRepository planRepository;

    private List<DayPlan> plannedList;

    private final CompositeDisposable disposables;

    private final PlanView view;

    public PlanPresenterImpl(PlanView view, PlanRepository planRepository) {
        this.planRepository = planRepository;
        this.view = view;
        disposables = new CompositeDisposable();
    }


    @Override
    public void onViewCreated() {
        var request = planRepository
                .getPlannedMealsForNextTwoWeeks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dayPlans -> plannedList = dayPlans,
                        throwable -> view.showPageError(throwable.getMessage())
                );
        disposables.add(request);
    }

    @Override
    public void removeMeal(PlannedMeal meal) {

        var request = planRepository
                .removePlannedMealFromDay(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( () -> view.showSuccessMessage("Item Removed Success Fully"),
                        throwable -> view.showErrorMessage(throwable.getMessage())
                );
        disposables.add(request);
    }


    @Override
    public void onDestroy() {
        disposables.clear();
    }
}
