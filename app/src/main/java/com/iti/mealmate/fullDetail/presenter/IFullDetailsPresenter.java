package com.iti.mealmate.fullDetail.presenter;

import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

public interface IFullDetailsPresenter {
    void getFullDetailedMeal(String mealID);
     void addToFav(Meal meal);

    void getFullLocalMeal(String mealID);

    void  addToPlan(PlannedMeal plannedMeal);

}
