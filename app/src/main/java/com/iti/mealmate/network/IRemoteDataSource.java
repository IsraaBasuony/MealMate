package com.iti.mealmate.network;

public interface IRemoteDataSource {
    void enqueueCallAllRandom(NetworkCallbackRandom networkCallbackRandom);
    void enqueueCallAllMeals(NetworkCallbackAllMeals networkCallbackAllMeals);

    void enqueueCallAllCategory(NetworkCallbackCagtegory networkCallbackCagtegory);

    void  enqueueCallAllIngredient(NetworkCallbackIngredient networkCallbackIngredient);
    void  enqueueCallAllCountry(NetworkCallbackCountry networkCallbackCountry );

    void  enqueueCallFullDetails(NetworkCallbackMealDetails networkCallbackMealDetails, String idMeal );
    void  enqueueCallMealsByIngredient(NetworkCallbackIngredientOne networkCallbackIngredientOne, String ingredientName);

    void  enqueueCallMealsByCategory(NetworkCallbackCategoryOne networkCallbackCategoryOne, String categoryName);

    void  enqueueCallMealsByArea(NetworkCallbackAreaOne networkCallbackAreaOne, String areaName);

}
