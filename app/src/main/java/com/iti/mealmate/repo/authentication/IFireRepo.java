package com.iti.mealmate.repo.authentication;

public interface IFireRepo {

    void signUp(String email , String pass);
    void signIn(String email , String pass);
    void logout();
}
