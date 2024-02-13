package com.iti.mealmate.home.presenter;

import com.iti.mealmate.home.view.IViewHome;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackRandom;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.List;

public class HomePresenter implements IHomePresenter, NetworkCallbackRandom, NetworkCallbackAllMeals {

    private IViewHome _view;
    private MealsRepo _repo;

    public HomePresenter(IViewHome _view, MealsRepo _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getRandomMeal() {

        _repo.getRandom(this);
    }

    @Override
    public void getAllMeals() {

        _repo.getAllMeals(this);
    }


    @Override
    public void onSuccessRandomResult(List<MealModel> mealModelList) {
        _view.showMealOfTheDay(mealModelList.get(0));
    }

    @Override
    public void onFailureRandomResult(String errorMsg) {

        _view.showErrMsg(errorMsg);
    }

    @Override
    public void onSuccessAllMealsResult(List<MealModel> mealModelList) {
        _view.showAllMeals(mealModelList);
    }

    @Override
    public void onFailureAllMealsResult(String errorMsg) {
        _view.showErrMsg(errorMsg);

    }
}
