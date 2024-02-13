package com.iti.mealmate.repo.meal;


import com.iti.mealmate.network.NetworkCallbackAllMeals;
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
}
