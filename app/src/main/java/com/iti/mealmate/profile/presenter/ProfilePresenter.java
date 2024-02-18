package com.iti.mealmate.profile.presenter;

import androidx.annotation.NonNull;

import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.network.FirebaseCallback;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.profile.view.IProfile;
import com.iti.mealmate.repo.authentication.FireRepo;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter implements IProfilePresenter, FirebaseCallback {

    private FireRepo _repo;
    private IProfile _view;
    private PlannedMealRepo _plannedRepo;
    private MealsRepo _mealRepo;

    public ProfilePresenter(IProfile _view, PlannedMealRepo _plannedRepo, MealsRepo _mealRepo) {
        this._repo = new FireRepo(this);
        this._view = _view;
        this._plannedRepo = _plannedRepo;
        this._mealRepo = _mealRepo;
    }

    @Override
    public void deleteFavMealsFromRoom() {

        _mealRepo.deleteFavTableRoom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.onLogoutFail(e.getMessage());
                    }
                });
    }

    @Override
    public void deletePlannedMealsFromRoom() {

        _plannedRepo.deletePlannedTableRoom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _view.onLogoutFail(e.getMessage());
                    }
                });
    }

    @Override
    public void logOut() {

        _repo.logout();
        deleteFavMealsFromRoom();
        deletePlannedMealsFromRoom();
    }

    @Override
    public void onSuccess(String userId) {
        _view.onLogoutSuccess();
    }

    @Override
    public void onFail(String msg) {
        _view.onLogoutFail(msg);

    }
}
