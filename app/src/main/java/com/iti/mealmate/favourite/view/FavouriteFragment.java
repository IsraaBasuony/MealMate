package com.iti.mealmate.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.databinding.FragmentFavouriteBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.favourite.presenter.FavouritePresenter;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements IFavourite, OnDeleteClickListener {

    FragmentFavouriteBinding binding;
    AllFavMealsAdapter adapter;
    FavouritePresenter presenter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AllFavMealsAdapter(getContext(),this, new ArrayList<>());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        presenter = new FavouritePresenter(MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())), this);
        binding.recFav.setLayoutManager(layoutManager);
        binding.recFav.setAdapter(adapter);
        presenter.getAllMeal();
    }

    @Override
    public void setFavMeals(Flowable<List<Meal>> mealList) {
        mealList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> adapter.setList((ArrayList<Meal>) item)
                );
    }

    @Override
    public void onDelClick(Meal meal) {
        presenter.deleteFavMeal(meal);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}