package com.iti.mealmate.db.plannedMeal;

import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ILocalPlannedMealsDataSource {

    Flowable<List<PlannedMeal>> getStrodePlannedMeals(String date);

    void deletePlanned(PlannedMeal plannedMeal);

    void insertPlanned(PlannedMeal plannedMeal);

}
