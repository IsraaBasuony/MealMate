package com.iti.mealmate.network;

import com.iti.mealmate.model.MealModel;

import java.util.ArrayList;
import java.util.List;

public interface NetworkCallbackRandom {

    public void onSuccessRandomResult(ArrayList<MealModel> mealModelList);
    public  void onFailureRandomResult(String errorMsg);

}
