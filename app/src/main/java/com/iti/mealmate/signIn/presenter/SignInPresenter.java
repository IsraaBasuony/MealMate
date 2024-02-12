package com.iti.mealmate.signIn.presenter;

import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.FirebaseCallback;
import com.iti.mealmate.repo.authentication.FireRepo;
import com.iti.mealmate.repo.authentication.IFireRepo;
import com.iti.mealmate.signIn.view.IViewSignIn;
import com.iti.mealmate.signIn.view.SignInFragment;

public class SignInPresenter implements ISignInPresenter, FirebaseCallback {

    private IViewSignIn _view;
    private IFireRepo _repo;

    public SignInPresenter(IViewSignIn iViewSignIn) {
        this._view = iViewSignIn;
        this._repo = new FireRepo(this);
    }

    @Override
    public void onSuccess(String userId) {


        UserSharedPref.setUserName(userId);
        _view.onLoginSuccess(userId);
    }

    @Override
    public void onFail(String msg) {
        _view.onLoginFail(msg);

    }

    @Override
    public void signin(String email, String pass) {
        _repo.signIn(email,pass);

    }

    @Override
    public void getFavMeals() {

    }

    @Override
    public void getWeekMeals() {

    }
}
