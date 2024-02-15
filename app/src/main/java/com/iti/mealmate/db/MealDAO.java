package com.iti.mealmate.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.iti.mealmate.model.Meal;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;
@Dao
public interface MealDAO {
    @Query("SELECT * From FavMeals")
    Flowable<List<Meal>> getAllFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavMeal(Meal meal);
    @Delete
    void deleteFromFv(Meal meal);
}
