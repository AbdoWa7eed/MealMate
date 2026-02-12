package com.iti.mealmate.data.meal.repo;

import com.iti.mealmate.core.error.AppErrorHandler;
import com.iti.mealmate.core.network.AppConnectivityManager;
import com.iti.mealmate.core.util.RxTask;
import com.iti.mealmate.data.meal.datasource.local.datasource.favorite.FavoriteLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.datasource.meal.MealLocalDataSource;
import com.iti.mealmate.data.meal.datasource.local.entity.CacheType;
import com.iti.mealmate.data.meal.datasource.local.entity.MealEntity;
import com.iti.mealmate.data.meal.datasource.remote.meal.MealRemoteDataSource;
import com.iti.mealmate.data.meal.model.entity.Meal;
import com.iti.mealmate.data.meal.model.entity.MealLight;
import com.iti.mealmate.data.meal.model.mapper.MealMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealRepositoryImpl implements MealRepository {

    private final MealRemoteDataSource remoteDataSource;
    private final AppConnectivityManager connectivityManager;
    private final FavoriteLocalDataSource favoriteLocalDataSource;

    private final MealLocalDataSource mealLocalDataSource;


    public MealRepositoryImpl(MealRemoteDataSource remoteDataSource,
                              AppConnectivityManager connectivityManager,
                              FavoriteLocalDataSource favoriteLocalDataSource,
                              MealLocalDataSource mealLocalDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.connectivityManager = connectivityManager;
        this.favoriteLocalDataSource = favoriteLocalDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public Flowable<Meal> getMealOfTheDay() {
        return mealLocalDataSource.getDailyMeal()
                .switchMap(list -> {
                    if (list.isEmpty()) {
                        return RxTask.withConnectivity(remoteDataSource.getMealOfTheDay(), connectivityManager)
                                .flatMap(this::setFavoriteStatus)
                                .flatMap(this::cacheMeal)
                                .toFlowable()
                                .onErrorResumeNext(Flowable::error);
                    } else {
                        return Flowable.just(list.get(0))
                                .map(MealMapper::cachedEntityToDomain);
                    }
                })
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Flowable<List<Meal>> getSuggestedMeals() {
        return mealLocalDataSource.getSuggestedMeals()
                .switchMap(list -> {
                    if (list.isEmpty()) {
                        return RxTask.withConnectivity(remoteDataSource.getSuggestedMeals(), connectivityManager)
                                .flatMap(this::setFavoriteStatusForList)
                                .flatMap(this::cacheMeals)
                                .toFlowable()
                                .onErrorResumeNext(Flowable::error);
                    } else {
                        return Flowable.just(list)
                                .map(MealMapper::cachedEntitiesToDomain);
                    }
                })
                .onErrorResumeNext(throwable -> Flowable.error(AppErrorHandler.handle(throwable)));
    }

    private Single<Meal> cacheMeal(Meal meal) {
        return mealLocalDataSource.insertMeal(MealMapper.domainToCachedEntity(meal, CacheType.DAILY))
                .andThen(Single.just(meal));
    }

    private Single<List<Meal>> cacheMeals(List<Meal> meals) {
        return mealLocalDataSource.insertMeals(MealMapper.domainToCachedEntities(meals, CacheType.TRENDING))
                .andThen(Single.just(meals));
    }

    @Override
    public Single<List<MealLight>> getMealsByIngredient(String ingredient) {
        return RxTask.withConnectivity(
                        remoteDataSource.getMealsByIngredient(ingredient),
                        connectivityManager
                )
                .map(MealMapper::toLightList)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }



    @Override
    public Single<List<MealLight>> searchMealsByName(String name) {
        return RxTask.withConnectivity(
                        remoteDataSource.searchMealsByName(name),
                        connectivityManager
                )
                .map(MealMapper::toLightList)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<MealLight>> getMealsByCategory(String category) {
        return RxTask.withConnectivity(
                        remoteDataSource.getMealsByCategory(category),
                        connectivityManager
                )
                .map(MealMapper::toLightList)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<List<MealLight>> getMealsByCountry(String country) {
        return RxTask.withConnectivity(
                        remoteDataSource.getMealsByCountry(country),
                        connectivityManager
                )
                .map(MealMapper::toLightList)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    @Override
    public Single<Meal> getMealById(String id) {
        return RxTask.withConnectivity(
                        remoteDataSource.getMealById(id),
                        connectivityManager
                )
                .map(MealMapper::toEntity)
                .flatMap(this::setFavoriteStatus)
                .onErrorResumeNext(throwable -> Single.error(AppErrorHandler.handle(throwable)));
    }

    private Single<Meal> setFavoriteStatus(Meal meal) {
        return favoriteLocalDataSource.isFavorite(meal.getId())
                .map(isFavorite -> {
                    meal.setFavorite(isFavorite);
                    return meal;
        });
    }

    private Single<List<Meal>> setFavoriteStatusForList(List<Meal> meals) {
        return favoriteLocalDataSource.getUserFavorites()
                .firstOrError()
                .map(favoriteEntities -> {
                    Set<String> favoriteIds = favoriteEntities.stream()
                            .map(MealEntity::getId)
                            .collect(Collectors.toSet());

                    meals.forEach(meal ->
                            meal.setFavorite(favoriteIds.contains(meal.getId())));

                    return meals;
                });
    }
}