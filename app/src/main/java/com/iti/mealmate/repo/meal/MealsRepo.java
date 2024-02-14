package com.iti.mealmate.repo.meal;


import com.iti.mealmate.network.NetworkCallbackAllMeals;
import com.iti.mealmate.network.NetworkCallbackCagtegory;
import com.iti.mealmate.network.NetworkCallbackCountry;
import com.iti.mealmate.network.NetworkCallbackIngredient;
import com.iti.mealmate.network.NetworkCallbackMealDetails;
import com.iti.mealmate.network.NetworkCallbackRandom;
import com.iti.mealmate.network.RemoteDataSource;

public class MealsRepo implements IMealsRepo {
    RemoteDataSource remoteDataSource;

    private  static MealsRepo repo = null;
    public  static  MealsRepo getInstance(RemoteDataSource remoteDataSource){
        if(repo == null){
            repo = new MealsRepo(remoteDataSource);
        }
        return repo;
    }

    private  MealsRepo(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
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


}
