package com.iti.mealmate.allMeals.presenter;

import com.iti.mealmate.model.Country;

import java.util.List;

public interface IPresenterAllMeals {
    void getAllMealsByIngrediant(String ingrediantName);
    void getAllMealsByCountry(String countryName);
    void getAllMealsByCategory(String categoryName);

}
