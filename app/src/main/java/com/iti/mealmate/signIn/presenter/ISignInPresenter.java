package com.iti.mealmate.signIn.presenter;

public interface ISignInPresenter {

    void signin(String email , String pass);

    void getFavMeals();

    void getWeekMeals();
}
