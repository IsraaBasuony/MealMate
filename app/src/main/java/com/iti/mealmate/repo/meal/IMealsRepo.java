package com.iti.mealmate.repo.meal;

import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackRandom;

public interface IMealsRepo {
    void getRandom(NetworkCallbackRandom networkCallbackRandom);
    void getAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals);



}
