package com.iti.mealmate.repo.plannedMeal;

import com.iti.mealmate.db.plannedMeal.DBPlannedDelegate;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface IPlannedMealRepo {


    Flowable<List<PlannedMeal>> getStoredPlannedMeals(String date);
    void deletePlannedMeal(PlannedMeal plannedMeal);
    void insertPlannedMeal(PlannedMeal plannedMeal);
    public void getLocalPlannedMeal(int plannedMealId, DBPlannedDelegate dbPlannedDelegate);
    Completable deletePlannedTableRoom();
}
