package com.iti.mealmate.network;

import com.iti.mealmate.model.Category;
import com.iti.mealmate.model.Category;

import java.util.ArrayList;

public interface NetworkCallbackCagtegory {

    public void onSuccessCategoryResult(ArrayList<Category> categoryList);
    public  void onFailureCategoryResult(String errorMsg);
}
