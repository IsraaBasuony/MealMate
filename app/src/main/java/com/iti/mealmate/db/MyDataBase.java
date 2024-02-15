package com.iti.mealmate.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.iti.mealmate.model.Meal;

@Database(entities = {Meal.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase myDataBase = null;
    public abstract MealDAO getMealDAO();
    public static synchronized MyDataBase getInstance(Context context) {
        if (myDataBase == null) {
            myDataBase = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "FavMeals").build();
        }
        return myDataBase;
    }
}
