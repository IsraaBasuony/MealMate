package com.iti.mealmate.favourite.view;
import com.iti.mealmate.model.Meal;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

public interface IFavourite {
     void setFavMeals(Flowable<List<Meal>> mealList);
}
