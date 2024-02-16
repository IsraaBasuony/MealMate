package com.iti.mealmate.repo.meal;


import com.iti.mealmate.db.favouriteMeal.DBDelegate;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackAreaOne;
import com.iti.mealmate.network.NetworkCallbackCagtegory;
import com.iti.mealmate.network.NetworkCallbackCategoryOne;
import com.iti.mealmate.network.NetworkCallbackCountry;
import com.iti.mealmate.network.NetworkCallbackIngredient;
import com.iti.mealmate.network.NetworkCallbackIngredientOne;
import com.iti.mealmate.network.NetworkCallbackMealDetails;
import com.iti.mealmate.network.NetworkCallbackRandom;
import com.iti.mealmate.network.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealsRepo implements IMealsRepo {
    RemoteDataSource remoteDataSource;
    LocalFavMealsDataSource localDataSource;
    LocalPlannedMealsDataSource localPlannedMealsDataSource;

    private  static MealsRepo repo = null;
    public  static  MealsRepo getInstance(RemoteDataSource remoteDataSource, LocalFavMealsDataSource localDataSource){
        if(repo == null){
            repo = new MealsRepo(remoteDataSource, localDataSource);
        }
        return repo;
    }
    private  MealsRepo(RemoteDataSource remoteDataSource, LocalFavMealsDataSource localFavMealsDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localFavMealsDataSource;
    }

    private  MealsRepo(RemoteDataSource remoteDataSource, LocalPlannedMealsDataSource localPlannedMealsDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localPlannedMealsDataSource = localPlannedMealsDataSource;
    }


    @Override
    public void getRandom(NetworkCallbackRandom networkCallbackRandom) {
        remoteDataSource.enqueueCallAllRandom(networkCallbackRandom);

    }

    @Override
    public void getAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals) {
        remoteDataSource.enqueueCallAllMeals(networkCallbackAllMeals);
    }

    @Override
    public void getAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory) {
        remoteDataSource.enqueueCallAllCategory(networkCallbackCagtegory);

    }

    @Override
    public void getAllIngredient(NetworkCallbackIngredient networkCallbackIngredient) {
        remoteDataSource.enqueueCallAllIngredient(networkCallbackIngredient);
    }

    @Override
    public void getAllCountry(NetworkCallbackCountry networkCallbackCountry) {

        remoteDataSource.enqueueCallAllCountry(networkCallbackCountry);
    }

    @Override
    public void getMealByID(NetworkCallbackMealDetails networkCallbackMealDetails, String mealID) {
        remoteDataSource.enqueueCallFullDetails(networkCallbackMealDetails,mealID);

    }
    @Override
    public void getMealsByIngrediantName(NetworkCallbackIngredientOne networkCallbackIngredientOne, String ingrediantName) {
        remoteDataSource.enqueueCallMealsByIngredient(networkCallbackIngredientOne, ingrediantName);
    }

    @Override
    public void getMealsByAreaName(NetworkCallbackAreaOne networkCallbackAreaOne, String areaName) {
        remoteDataSource.enqueueCallMealsByArea(networkCallbackAreaOne, areaName);
    }

    @Override
    public void getMealsBycategory(NetworkCallbackCategoryOne networkCallbackCategoryOne, String categoryName) {

        remoteDataSource.enqueueCallMealsByCategory(networkCallbackCategoryOne, categoryName);
    }

    @Override
    public Flowable<List<Meal>> getStoredFavMeals() {
        return  localDataSource.getStrodeFavMeals();
    }

    @Override
    public void delete(Meal meal) {
        localDataSource.delete(meal);
    }
    @Override
    public void insert(Meal meal) {
        localDataSource.insert(meal);
    }

    @Override
    public void getLocalMeal(String mealId, DBDelegate dbDelegate) {
        localDataSource.getLocalMeal(mealId , dbDelegate);
    }



}
