package com.iti.mealmate.network;

import com.iti.mealmate.model.CategoriesList;
import com.iti.mealmate.model.CountriesList;
import com.iti.mealmate.model.IngredientList;
import com.iti.mealmate.model.MealList;
import com.iti.mealmate.model.MealModelList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {

    @GET("random.php")
    Single<MealModelList> getRandomMeal();

    @GET("search.php")
    Single<MealModelList> getAllMealsByLetter(@Query("f") String letter);

    @GET("categories.php")
    Single<CategoriesList> getAllCategories();

    @GET("list.php?i=list")
    Single<IngredientList> getAllIngredients();

    @GET("list.php?a=list")
    Single<CountriesList> getAllCountries();

    @GET("lookup.php")
    Single<MealList> getFullDetailedMeal(@Query("i") String idMeal);

    @GET("filter.php")
    Single<MealModelList> getAllMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<MealModelList> getAllMealsByArea(@Query("a") String area);
    @GET("filter.php")
    Single<MealModelList>  getAllMealsByIngredient(@Query("i") String ingredient);

}
