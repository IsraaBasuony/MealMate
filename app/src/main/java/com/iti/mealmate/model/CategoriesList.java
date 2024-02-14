package com.iti.mealmate.model;

import java.util.ArrayList;

public class CategoriesList {

    private ArrayList<Category> categories;

    public CategoriesList(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}

