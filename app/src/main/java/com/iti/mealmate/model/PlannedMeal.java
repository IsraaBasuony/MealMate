package com.iti.mealmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlannedMeal")
public class PlannedMeal {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "meal")
    private Meal meal;

    @ColumnInfo(name = "date")
    private String date;

    public PlannedMeal() {
    }

    public PlannedMeal(@NonNull int id, Meal meal, String date) {
        this.id = id;
        this.meal = meal;
        this.date = date;
    }
    public PlannedMeal( Meal meal, String date) {
        this.meal = meal;
        this.date = date;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

