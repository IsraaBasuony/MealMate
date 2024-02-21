package com.iti.mealmate.fullDetail.presenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.mealmate.db.favouriteMeal.DBDelegate;
import com.iti.mealmate.db.plannedMeal.DBPlannedDelegate;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.fullDetail.view.IFullDetails;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.NetworkCallbackMealDetails;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

import java.util.List;

public class FullDetailsPresenter implements IFullDetailsPresenter, NetworkCallbackMealDetails, DBDelegate, DBPlannedDelegate {

    private IFullDetails _view;
    private MealsRepo _repo;
    private PlannedMealRepo _repoPlanned;

    private DatabaseReference ref;
    public FullDetailsPresenter(IFullDetails _view, MealsRepo _repo, PlannedMealRepo _repoPlanned) {
        this._view = _view;
        this._repo = _repo;
        this._repoPlanned = _repoPlanned;
        ref = FirebaseDatabase.getInstance().getReference();
    }



    @Override
    public void getFullDetailedMeal(String mealID) {
        _repo.getMealByID(this, mealID);

    }

    @Override
    public void addToFav(Meal meal) {
        _repo.insert(meal);
        insertMealToFireBase(meal);
    }
    @Override
    public void onSuccessRandomResult(List<Meal> mealList) {
        _view.onFullDetailedMealSuccess(mealList.get(0));

    }

    @Override
    public void onFailureRandomResult(String errorMsg) {
        _view.onFullDetailedMealFail(errorMsg);

    }

    @Override
    public void getFullFavLocalMeal(String mealID) {
        _repo.getLocalMeal(mealID, this);

    }

    @Override
    public void getFullPlannedLocalMeal(int plannedMealID) {
        _repoPlanned.getLocalPlannedMeal(plannedMealID,this);

    }

    @Override
    public void addToPlan(PlannedMeal plannedMeal) {
        _repoPlanned.insertPlannedMeal(plannedMeal );
    }

    @Override
    public void onSuccessFavLocalMeal(Meal meal) {
        _view.onFullDetailedMealSuccess(meal);

    }

    @Override
    public void onSuccessPlannedLocalMeal(Meal meal) {
        _view.onFullDetailedMealSuccess(meal);
    }

    public void insertMealToFireBase(Meal meal) {
        ref.child(UserSharedPref.getUserId())
                .child("favourites")
                .child(meal.getIdMeal())
                .setValue(meal).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
    }


}
