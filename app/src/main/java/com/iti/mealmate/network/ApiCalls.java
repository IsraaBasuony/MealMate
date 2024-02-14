package com.iti.mealmate.network;

import com.iti.mealmate.model.CategoriesList;
import com.iti.mealmate.model.CountriesList;
import com.iti.mealmate.model.IngredientList;
import com.iti.mealmate.model.MealModelList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {

    @GET("random.php")
    Call<MealModelList> getRandomMeal();

    @GET("search.php")
    Call<MealModelList> getAllMealsByLetter(@Query("f") String letter);

    @GET("categories.php")
    Call<CategoriesList> getAllCategories();

    @GET("list.php?i=list")
    Call<IngredientList> getAllIngredients();

    @GET("list.php?a=list")
    Call<CountriesList> getAllCountries();


}
