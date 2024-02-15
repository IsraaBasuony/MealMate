package com.iti.mealmate.favourite.presenter;

import com.iti.mealmate.favourite.view.IFavourite;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavouritePresenter implements  IFavouritePresenter{

    private MealsRepo _repo;
    private IFavourite _veiw;
    public FavouritePresenter(MealsRepo _repo, IFavourite _veiw) {
        this._repo = _repo;
        this._veiw = _veiw;
    }
    @Override
    public void getAllMeal() {
       _veiw.setFavMeals( _repo.getStoredFavMeals());
    }

    @Override
    public void deleteFavMeal(Meal meal) {
        _repo.delete(meal);

    }
}
