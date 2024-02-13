package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModelList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {

    @GET("random.php")
    Call<MealModelList> getRandomMeal();

    @GET("search.php")
    Call<MealModelList> getAllMealsByLetter(@Query("f") String letter);


}
