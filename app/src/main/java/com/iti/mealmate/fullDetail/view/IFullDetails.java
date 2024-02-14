package com.iti.mealmate.fullDetail.view;

import com.iti.mealmate.model.Meal;

public interface IFullDetails {

    void onFullDetailedMealSuccess(Meal meal);
    void onFullDetailedMealFail(String errorMessage);

}
