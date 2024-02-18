package com.iti.mealmate.db.plannedMeal;

import android.content.Context;

import com.iti.mealmate.db.MyDataBase;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalPlannedMealsDataSource implements ILocalPlannedMealsDataSource {

    private  static LocalPlannedMealsDataSource repo = null;
    private Context context;
    private PlannedMealDAO plannedMealDAO;
    public LocalPlannedMealsDataSource(Context context) {
        this.context = context;
        MyDataBase db = MyDataBase.getInstance(context.getApplicationContext());
        this.plannedMealDAO = db.getPlannedDAO();
    }
    public  static LocalPlannedMealsDataSource getInstance(Context context){
        if(repo == null)
            repo = new LocalPlannedMealsDataSource(context);
        return repo;
    }

    @Override
    public Flowable<List<PlannedMeal>> getStrodePlannedMeals(String date) {
        return plannedMealDAO.getAllPlannedMeals(date);
    }

    @Override
    public void deletePlanned(PlannedMeal plannedMeal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plannedMealDAO.deleteFromPlan(plannedMeal);
            }
        }).start();
    }
    @Override
    public void insertPlanned(PlannedMeal plannedMeal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plannedMealDAO.insertPlannedMeal(plannedMeal);
            }
        }).start();

    }

    @Override
    public void getLocalPlannedMeal(int plannedMealID, DBPlannedDelegate dbPlannedDelegate) {
        Single<PlannedMeal> observable = plannedMealDAO.getPlannedMealByID(plannedMealID);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> dbPlannedDelegate.onSuccessPlannedLocalMeal((Meal) o.getMeal()));
    }

    @Override
    public Completable deletePlannedTableRoom() {
        return plannedMealDAO.deletePlannedTableRoom();

    }
}
