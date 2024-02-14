package com.iti.mealmate.network;

import com.iti.mealmate.model.Ingredient;
import com.iti.mealmate.model.Ingredient;

import java.util.ArrayList;

public interface NetworkCallbackIngredient {

    public void onSuccessIngredientResult(ArrayList<Ingredient> ingredientList);
    public  void  onFailureIngredientResult(String errorMsg);
}
