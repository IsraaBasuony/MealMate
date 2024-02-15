package com.iti.mealmate.favourite.presenter;

import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.MealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface IFavouritePresenter {
    void getAllMeal();
    void deleteFavMeal(Meal meal);
}
