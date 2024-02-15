package com.iti.mealmate.fullDetail.presenter;

import com.iti.mealmate.model.Meal;

public interface IFullDetailsPresenter {
    void getFullDetailedMeal(String mealID);
     void addToFav(Meal meal);

}
