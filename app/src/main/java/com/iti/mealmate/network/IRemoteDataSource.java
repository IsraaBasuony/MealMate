package com.iti.mealmate.network;

public interface IRemoteDataSource {
    void enqueueCallAllRandom(NetworkCallbackRandom networkCallbackRandom);
    void enqueueCallAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals);

}
