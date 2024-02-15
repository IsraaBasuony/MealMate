package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface NetworkCallbackCategoryOne {
    void onCategoryNameSuccessfulResult(List<MealModel> MealsFromIngredient);
    void onFailureCategoryNameResult(String errorMessage);
}
