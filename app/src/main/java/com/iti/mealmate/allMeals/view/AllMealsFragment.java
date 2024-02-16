package com.iti.mealmate.allMeals.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.allMeals.presenter.AllMealsPresenter;
import com.iti.mealmate.allMeals.presenter.IPresenterAllMeals;
import com.iti.mealmate.databinding.FragmentAllMealsBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.model.MealModel;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllMealsFragment extends Fragment implements IAllMeals {

    FragmentAllMealsBinding binding;
    AllMealsFromSearchAdapter adapter;
    IPresenterAllMeals presenter;
    GridLayoutManager layoutManager;
    ArrayList<MealModel> mealList = new ArrayList<>();

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
        presenter = new AllMealsPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));
        binding.searchMealBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Observable<String> observable = Observable.create((ObservableEmitter<String> emitter) -> {
                    String searchTxt = charSequence.toString().toLowerCase();
                    emitter.onNext(searchTxt);
                }).debounce(500, TimeUnit.MILLISECONDS);
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(searchTxt -> {
                            filterMeals(searchTxt);
                        }, throwable -> Log.e("SearchFragment", "Error filtering", throwable));
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.resAllMeals.setLayoutManager(layoutManager);

        String categoryName = AllMealsFragmentArgs.fromBundle(getArguments()).getCategoryName();
        int id = AllMealsFragmentArgs.fromBundle(getArguments()).getId();

        Log.i("Bundle", "onViewCreated: " + categoryName +id);
        setRecV(categoryName, id );


    }


    private void filterMeals(String searchTxt) {
        Observable<MealModel> mealObservable = Observable.fromIterable(mealList)
                .filter(meal -> meal.getStrMeal().toLowerCase().contains(searchTxt));
        mealObservable.toList().subscribe(filteredCategories -> {
           adapter.setList(new ArrayList<>(filteredCategories));
            adapter.notifyDataSetChanged();
        });
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
        this.mealList = (ArrayList<MealModel>) mealsList;
    }

    @Override
    public void showErrMsg(String errorMsg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}