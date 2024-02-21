package com.iti.mealmate.Calender.presenter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.mealmate.Calender.view.ICalender;
import com.iti.mealmate.favourite.view.IFavourite;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

public class CalenderPresenter implements ICalenderPresenter {
    private PlannedMealRepo _repo;
    private ICalender _veiw;
    private DatabaseReference ref;

    public CalenderPresenter(PlannedMealRepo _repo, ICalender _veiw) {
        this._repo = _repo;
        this._veiw = _veiw;
        ref = FirebaseDatabase.getInstance().getReference();
    }
    @Override
    public void getPlannedMeal(String date) {
        _veiw.setPlannedMeal( _repo.getStoredPlannedMeals(date));
    }

    @Override
    public void deletePlannedMeal(PlannedMeal plannedMeal) {
        _repo.deletePlannedMeal(plannedMeal);
        deletePlannedFromFireBase(plannedMeal);
    }
    public void deletePlannedFromFireBase(PlannedMeal plannedMeal) {
        ref.child(UserSharedPref.getUserId())
                .child("planned")
                .child(plannedMeal.getId()+"")
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        deletePlannedMeal(plannedMeal);
                    }
                });
    }
}
