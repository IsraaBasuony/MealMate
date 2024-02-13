package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModelList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {

    @GET("random.php")
    Call<MealModelList> getRandomMeal();


}
