package com.iti.mealmate.allMeals.presenter;

import com.iti.mealmate.allMeals.view.IAllMeals;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.NetworkCallbackAreaOne;
import com.iti.mealmate.network.NetworkCallbackCategoryOne;
import com.iti.mealmate.network.NetworkCallbackIngredientOne;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.List;

public class AllMealsPresenter implements IPresenterAllMeals, NetworkCallbackAreaOne, NetworkCallbackCategoryOne, NetworkCallbackIngredientOne {

    private IAllMeals _view;
    private MealsRepo _repo;

    public AllMealsPresenter(IAllMeals _view, MealsRepo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void onAreaNameSuccessfulResult(List<MealModel> MealsFromArea) {

        _view.showAllMeals(MealsFromArea);
    }

    @Override
    public void onFailureAreaNameResult(String errorMessage) {
        _view.showErrMsg(errorMessage);

    }

    @Override
    public void onCategoryNameSuccessfulResult(List<MealModel> MealsFromCategory) {
        _view.showAllMeals(MealsFromCategory);

    }

    @Override
    public void onFailureCategoryNameResult(String errorMessage) {

        _view.showErrMsg(errorMessage);
    }

    @Override
    public void onIngredientNameSuccessfulResult(List<MealModel> MealsFromIngredient) {

        _view.showAllMeals(MealsFromIngredient);
    }

    @Override
    public void onFailureIngredientNameResult(String errorMessage) {
        _view.showErrMsg(errorMessage);

    }

    @Override
    public void getAllMealsByIngrediant(String ingrediantName) {
        _repo.getMealsByIngrediantName(this, ingrediantName);
    }

    @Override
    public void getAllMealsByCountry(String countryName) {
        _repo.getMealsByAreaName(this, countryName);

    }

    @Override
    public void getAllMealsByCategory(String categoryName) {
        _repo.getMealsBycategory(this, categoryName);

    }
}
