package com.iti.mealmate.Calender.presenter;

import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

public interface ICalenderPresenter {
    void getPlannedMeal(String date);
    void deletePlannedMeal(PlannedMeal plannedMeal);
}
