package com.iti.mealmate.network;

import android.util.Log;


import com.iti.mealmate.model.CategoriesList;
import com.iti.mealmate.model.CountriesList;
import com.iti.mealmate.model.IngredientList;
import com.iti.mealmate.model.MealList;
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
                    Log.i(TAG, "onResponse: random" + response.body().getMeals().size());
                    networkCallbackRandom.onSuccessRandomResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealModelList> call, Throwable t) {

                Log.i(TAG, "onFailure: random");
                networkCallbackRandom.onFailureRandomResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void enqueueCallAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals) {
        Call<MealModelList> call = apiCalls.getAllMealsByLetter("s");
        call.enqueue(new Callback<MealModelList>() {
            @Override
            public void onResponse(Call<MealModelList> call, Response<MealModelList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: Allmeals" + response.body().getMeals().get(0).getStrMeal());
                    networkCallbackAllMeals.onSuccessAllMealsResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealModelList> call, Throwable t) {

                Log.i(TAG, "onFailure:allmeals");
                networkCallbackAllMeals.onFailureAllMealsResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void enqueueCallAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory) {
        Call<CategoriesList> call = apiCalls.getAllCategories();
        call.enqueue(new Callback<CategoriesList>() {
            @Override
            public void onResponse(Call<CategoriesList> call, Response<CategoriesList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: category" + response.body().getCategories().size());
                    networkCallbackCagtegory.onSuccessCategoryResult(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoriesList> call, Throwable t) {
                Log.i(TAG, "onFailure: category");
                networkCallbackCagtegory.onFailureCategoryResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void enqueueCallAllIngredient(NetworkCallbackIngredient networkCallbackIngredient) {
        Call<IngredientList> call = apiCalls.getAllIngredients();
        call.enqueue((new Callback<IngredientList>() {
            @Override
            public void onResponse(Call<IngredientList> call, Response<IngredientList> response) {
                if (response.isSuccessful()) {
                    Log.i("Israa", "onResponse: Ingrediant" + response.body().getMeals().size());
                    networkCallbackIngredient.onSuccessIngredientResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<IngredientList> call, Throwable t) {

                Log.i("Israa", "onFailure: Ingrediant");
                networkCallbackIngredient.onFailureIngredientResult(t.getMessage());
                t.printStackTrace();
            }
        }));
    }

    @Override
    public void enqueueCallAllCountry(NetworkCallbackCountry networkCallbackCountry) {
        Call<CountriesList>call = apiCalls.getAllCountries();
        call.enqueue(new Callback<CountriesList>() {
            @Override
            public void onResponse(Call<CountriesList> call, Response<CountriesList> response) {
                if (response.isSuccessful()) {
                    Log.i("Israa", "onResponse: callBack country" + response.body().getMeals().size());
                    networkCallbackCountry.onSuccessCountryResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<CountriesList> call, Throwable t) {
                Log.i("Israa", "onFailure: countries");
                networkCallbackCountry.onFailureCountryResult(t.getMessage());
                t.printStackTrace();

            }
        });

    }

    @Override
    public void enqueueCallFullDetails(NetworkCallbackMealDetails networkCallbackMealDetails, String idMeal) {

        Call<MealList> call = apiCalls.getFullDetailedMeal(idMeal);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {

                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: random" + response.body().getMeals().get(0).getStrMeal());
                    networkCallbackMealDetails.onSuccessRandomResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {

                Log.i(TAG, "onFailure: random");
                networkCallbackMealDetails.onFailureRandomResult(t.getMessage());
                t.printStackTrace();
            }
        });

    }


}
