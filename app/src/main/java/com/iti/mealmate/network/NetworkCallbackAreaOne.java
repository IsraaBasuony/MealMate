package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface NetworkCallbackAreaOne {
    void onAreaNameSuccessfulResult(List<MealModel> MealsFromIngredient);
    void onFailureAreaNameResult(String errorMessage);
}
