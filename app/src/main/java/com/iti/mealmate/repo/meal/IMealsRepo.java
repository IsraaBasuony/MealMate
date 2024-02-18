package com.iti.mealmate.repo.meal;

import com.iti.mealmate.db.favouriteMeal.DBDelegate;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackAreaOne;
import com.iti.mealmate.network.NetworkCallbackCagtegory;
import com.iti.mealmate.network.NetworkCallbackCategoryOne;
import com.iti.mealmate.network.NetworkCallbackCountry;
import com.iti.mealmate.network.NetworkCallbackIngredient;
import com.iti.mealmate.network.NetworkCallbackIngredientOne;
import com.iti.mealmate.network.NetworkCallbackMealDetails;
import com.iti.mealmate.network.NetworkCallbackRandom;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface IMealsRepo {
    void getRandom(NetworkCallbackRandom networkCallbackRandom);
    void getAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals);

    void getAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory);
    void  getAllIngredient(NetworkCallbackIngredient networkCallbackIngredient);
    void getAllCountry(NetworkCallbackCountry networkCallbackCountry);
    void getMealByID(NetworkCallbackMealDetails networkCallbackMealDetails, String mealID);

    void getMealsByIngrediantName(NetworkCallbackIngredientOne networkCallbackIngredientOne, String ingrediantName);
    void getMealsByAreaName(NetworkCallbackAreaOne networkCallbackAreaOne, String areaName);

    void getMealsBycategory(NetworkCallbackCategoryOne networkCallbackCategoryOne, String categoryName);

    Flowable<List<Meal>> getStoredFavMeals();

    void delete(Meal meal);
    void insert(Meal meal);
    void getLocalMeal(String mealId, DBDelegate dbDelegate);
    Completable deleteFavTableRoom();



}
