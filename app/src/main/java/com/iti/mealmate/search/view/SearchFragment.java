package com.iti.mealmate.search.view;

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

import com.google.android.material.chip.Chip;
import com.iti.mealmate.databinding.FragmentSearchBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.model.Category;
import com.iti.mealmate.model.Country;
import com.iti.mealmate.model.Ingredient;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements ISearchFragment {
    FragmentSearchBinding binding;
    SearchPresenter searchPresenter;
    GridLayoutManager layoutManager;
    CategoriesAdapter categoriesAdapter;
    CountriesAdapter countriesAdapter;
    IngredientAdapter ingredientAdapter;
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<Country> countryList = new ArrayList<>();
    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private String type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        searchPresenter = new SearchPresenter(this, MealsRepo.getInstance(RemoteDataSource.getInstance(), LocalFavMealsDataSource.getInstance(getContext())));

        if (getArguments() != null) {
            type = getArguments().getString("type");
            switch (type) {
                case "Category":
                    searchPresenter.getCategories();
                    break;
                case "Country":
                    searchPresenter.getAllCountries();
                    break;
                case "Ingredient":
                    searchPresenter.getAllIngredients();
                    break;
            }
        } else {

            searchPresenter.getCategories();
        }
        setRecyclerV();


        binding.searchBox.addTextChangedListener(new TextWatcher() {
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
                            filterCategories(searchTxt);
                            filterIngredients(searchTxt);
                            filterCountries(searchTxt);
                        }, throwable -> Log.e("SearchFragment", "Error filtering", throwable));
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void filterCategories(String searchTxt) {
        Observable<Category> categoryObservable = Observable.fromIterable(categoryList)
                .filter(category -> category.getStrCategory().toLowerCase().contains(searchTxt));
        categoryObservable.toList().subscribe(filteredCategories -> {
            categoriesAdapter.setList(new ArrayList<>(filteredCategories));
            categoriesAdapter.notifyDataSetChanged();
        });
    }

    private void filterIngredients(String searchTxt) {
        Observable<Ingredient> ingredientObservable = Observable.fromIterable(ingredientList)
                .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(searchTxt));
        ingredientObservable.toList().subscribe(filteredIngredients -> {
            ingredientAdapter.setList(new ArrayList<>(filteredIngredients));
            ingredientAdapter.notifyDataSetChanged();
        });
    }

    private void filterCountries(String searchTxt) {
        Observable<Country> countryObservable = Observable.fromIterable(countryList)
                .filter(country -> country.getStrArea().toLowerCase().contains(searchTxt));
        countryObservable.toList().subscribe(filteredCountries -> {
            countriesAdapter.setList(new ArrayList<>(filteredCountries));
            countriesAdapter.notifyDataSetChanged();
        });
    }

    private void setRecyclerV() {
        for (int i = 0; i < binding.chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) binding.chipGroup.getChildAt(i);
            String chipTxt = chip.getText().toString();
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (chipTxt) {
                        case "Country":
                            binding.searchBoxLayout.setHint("Search by Country");
                            searchPresenter.getAllCountries();
                            binding.searchRecDefault.setAdapter(countriesAdapter);
                            break;
                        case "Ingredient":
                            binding.searchBoxLayout.setHint("Search by Ingredient");
                            searchPresenter.getAllIngredients();
                            binding.searchRecDefault.setAdapter(ingredientAdapter);
                            break;
                        case "Category":
                            binding.searchBoxLayout.setHint("Search by Category");
                            searchPresenter.getCategories();
                            binding.searchRecDefault.setAdapter(categoriesAdapter);
                            break;
                    }

                }
            });
        }
    }

    public void setType(String type) {
        this.type = type;
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
        getArguments().putString("type", type);
    }


    @Override
    public void showAllCategories(List<Category> categoryList) {
        this.categoryList = (ArrayList<Category>) categoryList;
        setType("Category");
        categoriesAdapter.setList((ArrayList<Category>) categoryList);
        binding.searchRecDefault.setAdapter(categoriesAdapter);
    }

    @Override
    public void showAllIngredients(List<Ingredient> ingredientList) {
        this.ingredientList = (ArrayList<Ingredient>) ingredientList;
        setType("Ingredient");
        ingredientAdapter.setList((ArrayList<Ingredient>) ingredientList);
        binding.searchRecDefault.setAdapter(ingredientAdapter);
        Log.i("Israa", "showAllIngredients: " + ingredientList.get(1).getStrIngredient());
    }

    @Override
    public void showAllCountries(List<Country> countryList) {
        this.countryList = (ArrayList<Country>) countryList;
        setType("Country");
        countriesAdapter.setList((ArrayList<Country>) countryList);
        binding.searchRecDefault.setAdapter(countriesAdapter);
    }

    @Override
    public void showErrMsg(String errorMsg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMsg).setTitle("ERROR");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}