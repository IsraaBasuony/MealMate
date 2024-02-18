package com.iti.mealmate.db.plannedMeal;

import com.iti.mealmate.db.favouriteMeal.DBDelegate;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface ILocalPlannedMealsDataSource {

    Flowable<List<PlannedMeal>> getStrodePlannedMeals(String date);

    void deletePlanned(PlannedMeal plannedMeal);

    void insertPlanned(PlannedMeal plannedMeal);

    void getLocalPlannedMeal(int plannedMealID, DBPlannedDelegate dbPlannedDelegate);
    Completable deletePlannedTableRoom();


}
