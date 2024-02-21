package com.iti.mealmate.signIn.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.UserSharedPref;
import com.iti.mealmate.network.FirebaseCallback;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.authentication.FireRepo;
import com.iti.mealmate.repo.authentication.IFireRepo;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.signIn.view.IViewSignIn;
import com.iti.mealmate.signIn.view.SignInFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInPresenter implements ISignInPresenter, FirebaseCallback {

    private IViewSignIn _view;
    private IFireRepo _repo;
    private MealsRepo _mealRepo;
    private DatabaseReference ref;

    public SignInPresenter(IViewSignIn iViewSignIn, MealsRepo mealsRepo) {
        this._view = iViewSignIn;
        this._repo = new FireRepo(this);
        this._mealRepo = mealsRepo;
        ref =  FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onSuccess(String userId) {

        UserSharedPref.setUserId(userId);
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

        ref.child(UserSharedPref.getUserId())
                .child("favourites")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Meal meal = snapshot1.getValue(Meal.class);
                                _mealRepo.insert(meal);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                    }

                });
    }
}
