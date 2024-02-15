package com.iti.mealmate.allMeals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.R;
import com.iti.mealmate.allMeals.presenter.AllMealsPresenter;
import com.iti.mealmate.allMeals.presenter.IPresenterAllMeals;
import com.iti.mealmate.databinding.FragmentAllMealsBinding;
import com.iti.mealmate.fullDetail.view.FullDetailsFragmentArgs;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.ArrayList;
import java.util.List;

public class AllMealsFragment extends Fragment implements IAllMeals {

    FragmentAllMealsBinding binding;
    AllMealsFromSearchAdapter adapter;
    IPresenterAllMeals presenter;
    GridLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllMealsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new AllMealsFromSearchAdapter(getContext(),new ArrayList<>());
        presenter = new AllMealsPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance()));
        binding.resAllMeals.setLayoutManager(layoutManager);

        String categoryName = AllMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        int id = AllMealsFragmentArgs.fromBundle(getArguments()).getId();

        Log.i("Bundle", "onViewCreated: " + categoryName +id);
        setRecV(categoryName, id );
    }

    private void  setRecV(String categoryName, int id){
        if(id != 0){
            if(id == 1 ){
                presenter.getAllMealsByCategory(categoryName);
                binding.resAllMeals.setAdapter(adapter);
            }else if(id == 2){
                presenter.getAllMealsByIngrediant(categoryName);
                binding.resAllMeals.setAdapter(adapter);
            }else if(id == 3){
                presenter.getAllMealsByCountry(categoryName);
                binding.resAllMeals.setAdapter(adapter);
            }
        }
    }

    @Override
    public void showAllMeals(List<MealModel> mealsList) {
        adapter.setList((ArrayList<MealModel>) mealsList);
    }

    @Override
    public void showErrMsg(String errorMsg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}