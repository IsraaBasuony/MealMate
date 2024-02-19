package com.iti.mealmate.home.view;

import com.iti.mealmate.model.Category;
import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface IViewHome {

    void showMealOfTheDay(MealModel mealModel);
    void showAllMeals(List<MealModel> meals);
    public void showErrMsg(String errorMsg);
    void showAllCategories(List<Category> categoryList);
}
