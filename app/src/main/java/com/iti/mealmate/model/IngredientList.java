package com.iti.mealmate.model;

import java.util.ArrayList;

public class IngredientList {

    private ArrayList <Ingredient> meals;

    public IngredientList(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }

    public ArrayList<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Ingredient> meals) {
        this.meals = meals;
    }
}
