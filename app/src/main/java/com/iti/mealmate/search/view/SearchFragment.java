package com.iti.mealmate.search.view;

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

import com.google.android.material.chip.Chip;
import com.iti.mealmate.databinding.FragmentSearchBinding;
import com.iti.mealmate.model.Category;
import com.iti.mealmate.model.Country;
import com.iti.mealmate.model.Ingredient;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ISearchFragment {

    FragmentSearchBinding binding;
    SearchPresenter searchPresenter;
    GridLayoutManager layoutManager;

    CategoriesAdapter categoriesAdapter;
    CountriesAdapter countriesAdapter;
    IngredientAdapter ingredientAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesAdapter = new CategoriesAdapter(getContext(), new ArrayList<>());
        countriesAdapter = new CountriesAdapter(getContext(), new ArrayList<>());
        ingredientAdapter = new IngredientAdapter(getContext(), new ArrayList<>());
        layoutManager = new GridLayoutManager(getContext(), 2);
        binding.searchRecDefault.setLayoutManager(layoutManager);
        searchPresenter = new SearchPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance()));
        searchPresenter.getAllCountries();
        searchPresenter.getAllIngredients();
        searchPresenter.getCategories();

        setRecyclerV();

    }

    private void setRecyclerV() {
        for (int i = 0; i < binding.chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.chipGroup.getChildAt(i);
            String chipTxt = chip.getText().toString();
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (chipTxt.equals("Country")){
                        binding.searchRecDefault.setAdapter(countriesAdapter);
                    } else if (chipTxt.equals("Ingredient")){
                        binding.searchRecDefault.setAdapter(ingredientAdapter);
                    } else if(chipTxt.equals("Category")){
                        binding.searchRecDefault.setAdapter(categoriesAdapter);
                    }
                }
            });
        }
    }
    @Override
    public void showAllCategories(List<Category> categoryList) {

        categoriesAdapter.setList((ArrayList<Category>) categoryList);
    }

    @Override
    public void showAllIngredients(List<Ingredient> ingredientList) {

        ingredientAdapter.setList((ArrayList<Ingredient>) ingredientList);
        Log.i("Israa", "showAllIngredients: "+ingredientList.get(1).getStrIngredient());
    }

    @Override
    public void showAllCountries(List<Country> countryList) {
        countriesAdapter.setList((ArrayList<Country>) countryList);
    }

    @Override
    public void showErrMsg(String errorMsg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}