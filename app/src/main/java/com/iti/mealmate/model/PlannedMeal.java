package com.iti.mealmate.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlannedMeal")
public class PlannedMeal {
    @ColumnInfo(name = "meal")
    private Meal meal;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "date")
    private String date;

    public PlannedMeal() {
    }

    public PlannedMeal( Meal meal,@NonNull String date) {
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

