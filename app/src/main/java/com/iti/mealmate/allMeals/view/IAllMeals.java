package com.iti.mealmate.allMeals.view;
import com.iti.mealmate.model.MealModel;
import java.util.List;

public interface IAllMeals {
    void showAllMeals(List<MealModel> mealsList);

    void showErrMsg(String errorMsg);
}
