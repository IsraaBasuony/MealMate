package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface NetworkCallbackAllMeals {
    public void onSuccessAllMealsResult(List<MealModel> mealModelList);
    public  void onFailureAllMealsResult(String errorMsg);
}
