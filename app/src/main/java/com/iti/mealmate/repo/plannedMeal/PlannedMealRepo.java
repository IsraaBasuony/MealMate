package com.iti.mealmate.repo.plannedMeal;
import com.iti.mealmate.db.favouriteMeal.DBDelegate;
import com.iti.mealmate.db.plannedMeal.DBPlannedDelegate;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.network.RemoteDataSource;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlannedMealRepo implements IPlannedMealRepo{


    RemoteDataSource remoteDataSource;
    LocalPlannedMealsDataSource localPlannedMealsDataSource;
    private  static PlannedMealRepo repo = null;
    public  static  PlannedMealRepo getInstance(RemoteDataSource remoteDataSource, LocalPlannedMealsDataSource localDataSource){
        if(repo == null){
            repo = new PlannedMealRepo(remoteDataSource, localDataSource);
        }
        return repo;
    }

    private  PlannedMealRepo(RemoteDataSource remoteDataSource, LocalPlannedMealsDataSource localPlannedMealsDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localPlannedMealsDataSource = localPlannedMealsDataSource;
    }



    @Override
    public Flowable<List<PlannedMeal>> getStoredPlannedMeals(String date) {
        return localPlannedMealsDataSource.getStrodePlannedMeals(date);
    }

    @Override
    public void deletePlannedMeal(PlannedMeal plannedMeal) {
        localPlannedMealsDataSource.deletePlanned(plannedMeal);

    }

    @Override
    public void insertPlannedMeal(PlannedMeal plannedMeal) {
        localPlannedMealsDataSource.insertPlanned(plannedMeal);

    }

    @Override
    public void getLocalPlannedMeal(int plannedMealId, DBPlannedDelegate dbPlannedDelegate) {
        localPlannedMealsDataSource.getLocalPlannedMeal(plannedMealId, dbPlannedDelegate);
    }

    @Override
    public Completable deletePlannedTableRoom() {
        return localPlannedMealsDataSource.deletePlannedTableRoom();
    }


}
