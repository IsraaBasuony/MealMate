package com.iti.mealmate.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.iti.mealmate.db.favouriteMeal.Converters;
import com.iti.mealmate.db.favouriteMeal.MealDAO;
import com.iti.mealmate.db.plannedMeal.MealConverter;
import com.iti.mealmate.db.plannedMeal.PlannedMealDAO;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

@Database(entities = {Meal.class, PlannedMeal.class}, version = 3)
@TypeConverters({Converters.class, MealConverter.class})
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase myDataBase = null;
    public abstract MealDAO getMealDAO();
    public abstract PlannedMealDAO getPlannedDAO();
    public static synchronized MyDataBase getInstance(Context context) {
        if (myDataBase == null) {
            myDataBase = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "MealsDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDataBase;
    }
}
