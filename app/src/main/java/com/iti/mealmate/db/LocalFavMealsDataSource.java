package com.iti.mealmate.db;

import android.content.Context;
import com.iti.mealmate.model.Meal;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

public class LocalFavMealsDataSource implements  ILocalFavMealsDataSource {
    private  static LocalFavMealsDataSource repo = null;
    private Context context;
    private MealDAO mealDAO;
    private Flowable<List<Meal>> mealList;
    private LocalFavMealsDataSource(Context context){
        this.context = context;
        MyDataBase db = MyDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getMealDAO();
        mealList =  mealDAO.getAllFavMeals();
    }
    public  static LocalFavMealsDataSource getInstance(Context context){
        if(repo == null)
            repo = new LocalFavMealsDataSource(context);
        return repo;
    }
    @Override
    public Flowable<List<Meal>> getStrodeFavMeals() {
        return mealList;
    }
    @Override
    public void delete(Meal meal) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteFromFv(meal);
            }
        }).start();
    }
    @Override
    public void insert(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertFavMeal(meal);
            }
        }).start();
    }
}
