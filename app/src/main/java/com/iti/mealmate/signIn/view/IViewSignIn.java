package com.iti.mealmate.signIn.view;

public interface IViewSignIn {
    void onLoginSuccess(String userId);
    void onLoginFail(String message);
}
