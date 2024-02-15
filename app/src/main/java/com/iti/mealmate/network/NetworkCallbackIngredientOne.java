package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface NetworkCallbackIngredientOne {
    void onIngredientNameSuccessfulResult(List<MealModel> MealsFromIngredient);
    void onFailureIngredientNameResult(String errorMessage);
}
