package com.iti.mealmate.db.favouriteMeal;

import android.content.Context;

import com.iti.mealmate.db.MyDataBase;
import com.iti.mealmate.model.Meal;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalFavMealsDataSource implements ILocalFavMealsDataSource {
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

    @Override
    public void getLocalMeal(String mealID, DBDelegate dbDelegate) {
        Single<Meal> observable = mealDAO.getMealByID(mealID);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> dbDelegate.onSuccessLocalMeal((Meal) o));
    }
}
