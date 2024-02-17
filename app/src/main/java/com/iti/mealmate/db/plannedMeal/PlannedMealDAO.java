package com.iti.mealmate.db.plannedMeal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
@Dao
public interface PlannedMealDAO {
    @Query("SELECT * From PlannedMeal WHERE date = :date")
    Flowable<List<PlannedMeal>> getAllPlannedMeals(String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlannedMeal(PlannedMeal plannedMeal);
    @Delete
    void deleteFromPlan(PlannedMeal plannedMeal);

    @Query("SELECT * From PlannedMeal WHERE id = :id")
    Single<PlannedMeal> getPlannedMealByID(int id);
}
