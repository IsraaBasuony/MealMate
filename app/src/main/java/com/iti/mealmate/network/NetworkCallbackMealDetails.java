package com.iti.mealmate.network;

import com.iti.mealmate.model.Meal;
import java.util.List;

public interface NetworkCallbackMealDetails {



        public void onSuccessRandomResult(List<Meal> mealList);
        public  void onFailureRandomResult(String errorMsg);


}
