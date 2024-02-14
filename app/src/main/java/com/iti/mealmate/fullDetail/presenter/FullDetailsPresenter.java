package com.iti.mealmate.fullDetail.presenter;

import com.iti.mealmate.fullDetail.view.IFullDetails;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.NetworkCallbackMealDetails;
import com.iti.mealmate.network.NetworkCallbackRandom;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.List;

public class FullDetailsPresenter implements IFullDetailsPresenter, NetworkCallbackMealDetails {

    private IFullDetails _view;
    private MealsRepo _repo;

    public FullDetailsPresenter(IFullDetails _view, MealsRepo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getFullDetailedMeal(String mealID) {
        _repo.getMealByID(this, mealID);

    }


    @Override
    public void onSuccessRandomResult(List<Meal> mealList) {
        _view.onFullDetailedMealSuccess(mealList.get(0));

    }

    @Override
    public void onFailureRandomResult(String errorMsg) {
        _view.onFullDetailedMealFail(errorMsg);

    }
}
