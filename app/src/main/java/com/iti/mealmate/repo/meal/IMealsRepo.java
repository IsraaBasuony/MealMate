package com.iti.mealmate.repo.meal;

import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackCagtegory;
import com.iti.mealmate.network.NetworkCallbackCountry;
import com.iti.mealmate.network.NetworkCallbackIngredient;
import com.iti.mealmate.network.NetworkCallbackRandom;

public interface IMealsRepo {
    void getRandom(NetworkCallbackRandom networkCallbackRandom);
    void getAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals);

    void getAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory);
    void  getAllIngredient(NetworkCallbackIngredient networkCallbackIngredient);
    void getAllCountry(NetworkCallbackCountry networkCallbackCountry);



}
