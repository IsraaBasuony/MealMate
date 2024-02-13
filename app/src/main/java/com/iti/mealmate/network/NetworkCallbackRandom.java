package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.List;

public interface NetworkCallbackRandom {

    public void onSuccessRandomResult(List<MealModel> mealModelList);
    public  void onFailureRandomResult(String errorMsg);


}
