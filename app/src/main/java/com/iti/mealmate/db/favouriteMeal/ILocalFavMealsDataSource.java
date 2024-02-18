package com.iti.mealmate.db.favouriteMeal;
import com.iti.mealmate.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface ILocalFavMealsDataSource {
    Flowable<List<Meal>> getStrodeFavMeals();
    void delete(Meal meal);

    void insert(Meal meal);

    void getLocalMeal(String mealID, DBDelegate dbDelegate);
    Completable deleteFavTableRoom();
}
