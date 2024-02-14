package com.iti.mealmate.model;

    import java.util.ArrayList;

    public class CountriesList {
        ArrayList<Country> meals;

        public ArrayList<Country> getMeals() {
            return meals;
        }

        public void setMeals(ArrayList<Country> meals) {
            this.meals = meals;
        }

        public CountriesList(ArrayList<Country> meals) {
            this.meals = meals;
        }
    }
