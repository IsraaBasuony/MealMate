package com.iti.mealmate.Calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iti.mealmate.Calender.presenter.CalenderPresenter;
import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentCalenderBinding;
import com.iti.mealmate.databinding.FragmentFavouriteBinding;
import com.iti.mealmate.db.favouriteMeal.LocalFavMealsDataSource;
import com.iti.mealmate.db.plannedMeal.LocalPlannedMealsDataSource;
import com.iti.mealmate.favourite.presenter.FavouritePresenter;
import com.iti.mealmate.favourite.view.AllFavMealsAdapter;
import com.iti.mealmate.model.Meal;
import com.iti.mealmate.model.PlannedMeal;
import com.iti.mealmate.network.RemoteDataSource;
import com.iti.mealmate.repo.meal.MealsRepo;
import com.iti.mealmate.repo.plannedMeal.PlannedMealRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderFragment extends Fragment implements ICalender,OnDeleteClickListener {

    FragmentCalenderBinding binding;
    CalenderPresenter presenter;
    AllPlannedMealsAdapter adapter;
    LinearLayoutManager layoutManager;
    String date;
    PlannedMeal plannedMeal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalenderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AllPlannedMealsAdapter(getContext(),this, new ArrayList<>());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        presenter = new CalenderPresenter(PlannedMealRepo.getInstance(RemoteDataSource.getInstance(), LocalPlannedMealsDataSource.getInstance(getContext())), this);
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = "" + i2 + "" + i1 + "" + i;
                presenter.getPlannedMeal(date);
            }
        });

        binding.plannedRec.setLayoutManager(layoutManager);
        binding.plannedRec.setAdapter(adapter);


    }

    @Override
    public void setPlannedMeal(Flowable<List<PlannedMeal>> mealList) {
        mealList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(plannedMeals -> filterPlannedMealsByDate(plannedMeals)) // Filter by date
                .subscribe(
                        filteredList -> adapter.setList((ArrayList<PlannedMeal>) filteredList),
                        e -> Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show());
    }

    private List<PlannedMeal> filterPlannedMealsByDate(List<PlannedMeal> plannedMeals) {
        List<PlannedMeal> filteredList = new ArrayList<>();
        // Assuming date is stored in the format "yyyyMMdd"
        for (PlannedMeal plannedMeal : plannedMeals) {
            if (plannedMeal.getDate().equals(date)) {
                filteredList.add(plannedMeal);
            }
        }
        return filteredList;
    }


    @Override
    public void onDelClick(PlannedMeal meal) {

        presenter.deletePlannedMeal(meal);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}