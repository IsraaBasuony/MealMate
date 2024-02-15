package com.iti.mealmate.search.view;

import com.iti.mealmate.model.Category;
import com.iti.mealmate.model.Country;
import com.iti.mealmate.model.Ingredient;

import java.util.List;

public interface ISearchFragment {

    void showAllCategories(List<Category> categoryList);

    void showAllIngredients(List<Ingredient> ingredientList);

    void showAllCountries(List<Country> countryList);

    void showErrMsg(String errorMsg);

}
