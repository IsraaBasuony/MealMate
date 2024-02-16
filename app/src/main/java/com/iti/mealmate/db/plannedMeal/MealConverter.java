package com.iti.mealmate.db.plannedMeal;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.iti.mealmate.model.Meal;
public class MealConverter {
        @TypeConverter
        public String fromMealToString(Meal meal) {
            return new Gson().toJson(meal);
        }
        @TypeConverter
        public Meal fromStringToMeal(String string) {
            return new Gson().fromJson(string, Meal.class);
        }
}
