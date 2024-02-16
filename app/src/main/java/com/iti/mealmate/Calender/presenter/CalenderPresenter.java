package com.iti.mealmate.Calender.presenter;

import com.iti.mealmate.Calender.view.ICalender;
import com.iti.mealmate.favourite.view.IFavourite;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

public class CalenderPresenter implements ICalenderPresenter {

    private PlannedMealRepo _repo;
    private ICalender _veiw;
    public CalenderPresenter(PlannedMealRepo _repo, ICalender _veiw) {
        this._repo = _repo;
        this._veiw = _veiw;
    }
    @Override
    public void getPlannedMeal(String date) {
        _veiw.setPlannedMeal( _repo.getStoredPlannedMeals(date));
    }

    @Override
    public void deletePlannedMeal(PlannedMeal plannedMeal) {
        _repo.deletePlannedMeal(plannedMeal);
    }
}
