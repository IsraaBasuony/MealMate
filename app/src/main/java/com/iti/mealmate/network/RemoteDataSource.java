package com.iti.mealmate.network;

import android.util.Log;


import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.model.MealModelList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource implements IRemoteDataSource {

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String TAG = "Israa";
    private List<MealModel> mealModelList;
    private static RemoteDataSource retrofit = null;
    private ApiCalls apiCalls;

    public RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiCalls = retrofit.create(ApiCalls.class);
        this.mealModelList = new ArrayList<>();
    }

    public static RemoteDataSource getInstance() {
        if (retrofit == null) {
            retrofit = new RemoteDataSource();
        }
        return retrofit;
    }

    @Override
    public void enqueueCallAllRandom(NetworkCallbackRandom networkCallbackRandom) {
        Call<MealModelList> call = apiCalls.getRandomMeal();
        call.enqueue(new Callback<MealModelList>() {
            @Override
            public void onResponse(Call<MealModelList> call, Response<MealModelList> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: callBack" + response.body().getMeals().size());
                    networkCallbackRandom.onSuccessRandomResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealModelList> call, Throwable t) {

                Log.i(TAG, "onFailure: CallBack");
                networkCallbackRandom.onFailureRandomResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }
}
