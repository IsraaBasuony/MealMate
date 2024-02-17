package com.iti.mealmate.signUp.presenter;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.FirebaseCallback;
import com.iti.mealmate.repo.authentication.FireRepo;
import com.iti.mealmate.repo.authentication.IFireRepo;
import com.iti.mealmate.signUp.view.IViewSignUp;

public class SignUpPresenter implements ISignUpPresenter, FirebaseCallback {
    private IViewSignUp _view;
    private IFireRepo _repo;

    public SignUpPresenter(IViewSignUp _view) {
        this._view = _view;
        this._repo = new FireRepo(this);
    }

    @Override
    public void signUp(String email, String pass) {
        _repo.signUp(email, pass);


    }

    @Override
    public void signIn(String email, String pass) {
        _repo.signIn(email, pass);

    }

    @Override
    public void onSuccess(String userId) {
        UserSharedPref.setUserId(userId);
        _view.onRegisterSuccess(userId);
    }

    @Override
    public void onFail(String msg) {
        _view.onRegisterFail(msg);
    }
}
