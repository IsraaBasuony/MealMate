package com.iti.mealmate.network;

import android.util.Log;


import com.iti.mealmate.model.CategoriesList;
import com.iti.mealmate.model.CountriesList;
import com.iti.mealmate.model.IngredientList;
import com.iti.mealmate.model.MealList;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.model.MealModelList;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
        Single<MealModelList> call = apiCalls.getRandomMeal();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i(TAG, "onResponse: random" + response);
                    networkCallbackRandom.onSuccessRandomResult(response.getMeals());

                }, error -> {
                    Log.i(TAG, "onFailure: random");
                    networkCallbackRandom.onFailureRandomResult(error.getMessage());
                    error.printStackTrace();
                });


    }

    @Override
    public void enqueueCallAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals) {
        Single<MealModelList> call = apiCalls.getAllMealsByLetter("s");
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackAllMeals.onSuccessAllMealsResult(response.getMeals()),
                        error -> networkCallbackAllMeals.onFailureAllMealsResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory) {
        Single<CategoriesList> call = apiCalls.getAllCategories();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackCagtegory.onSuccessCategoryResult(response.getCategories()),
                        error -> networkCallbackCagtegory.onFailureCategoryResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallAllIngredient(NetworkCallbackIngredient networkCallbackIngredient) {
        Single<IngredientList> call = apiCalls.getAllIngredients();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackIngredient.onSuccessIngredientResult(response.getMeals()),
                        error -> networkCallbackIngredient.onFailureIngredientResult(error.getMessage())
                );

    }

    @Override
    public void enqueueCallAllCountry(NetworkCallbackCountry networkCallbackCountry) {
        Single<CountriesList> call = apiCalls.getAllCountries();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackCountry.onSuccessCountryResult(response.getMeals()),
                        error -> networkCallbackCountry.onFailureCountryResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallFullDetails(NetworkCallbackMealDetails networkCallbackMealDetails, String idMeal) {

        Single<MealList> call = apiCalls.getFullDetailedMeal(idMeal);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackMealDetails.onSuccessRandomResult(response.getMeals()),
                        error -> networkCallbackMealDetails.onFailureRandomResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallMealsByIngredient(NetworkCallbackIngredientOne networkCallbackIngredientOne, String ingredientName) {
        Single<MealModelList> call = apiCalls.getAllMealsByIngredient(ingredientName);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackIngredientOne.onIngredientNameSuccessfulResult(response.getMeals()),
                        error -> networkCallbackIngredientOne.onFailureIngredientNameResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallMealsByCategory(NetworkCallbackCategoryOne networkCallbackCategoryOne, String categoryName) {
        Single<MealModelList> call = apiCalls.getAllMealsByCategory(categoryName);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> networkCallbackCategoryOne.onCategoryNameSuccessfulResult(response.getMeals()),
                        error -> networkCallbackCategoryOne.onFailureCategoryNameResult(error.getMessage())
                );
    }

    @Override
    public void enqueueCallMealsByArea(NetworkCallbackAreaOne networkCallbackAreaOne, String areaName) {

        Single<MealModelList> call = apiCalls.getAllMealsByArea(areaName);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response ->networkCallbackAreaOne.onAreaNameSuccessfulResult(response.getMeals()),
                        error ->  networkCallbackAreaOne.onFailureAreaNameResult(error.getMessage())
                );
    }


}
